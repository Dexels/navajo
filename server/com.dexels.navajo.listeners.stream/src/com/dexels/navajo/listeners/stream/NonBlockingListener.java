package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.servlet.RequestPublisher;
import org.reactivestreams.servlet.ResponseSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.authentication.api.AuthenticationMethodBuilder;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.StreamCompress;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.reactive.RunningReactiveScriptsImpl;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.LocalClient;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class NonBlockingListener extends HttpServlet {
	private static final long serialVersionUID = -4381216748627396838L;

	private LocalClient localClient;
				
	private final static Logger logger = LoggerFactory.getLogger(NonBlockingListener.class);

	private AuthenticationMethodBuilder authMethodBuilder;

	private ReactiveScriptRunner reactiveScriptEnvironment;
	
//	private final static ObjectMapper objectMapper = new ObjectMapper();

	private RunningReactiveScripts runningReactiveScripts = new RunningReactiveScriptsImpl();
	

	
	public NonBlockingListener() {
		Observable.interval(10, TimeUnit.SECONDS)
			.subscribe(i->{
				List<String> scripts = runningReactiveScripts.services();
				if(scripts.size()>0) {
					logger.info("Running scripts: "+scripts);
				}
			});
	}
	
	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}

    public void setAuthenticationMethodBuilder(AuthenticationMethodBuilder amb) {
        this.authMethodBuilder = amb;
    }

    public void clearAuthenticationMethodBuilder(AuthenticationMethodBuilder eventAdmin) {
        this.authMethodBuilder = null;
    }
    
    public void setReactiveScriptEnvironment(ReactiveScriptRunner env) {
    		this.reactiveScriptEnvironment = env;
    }

    public void clearReactiveScriptEnvironment(ReactiveScriptRunner env) {
		this.reactiveScriptEnvironment = null;
    }

	private static Map<String, Object> extractHeaders(HttpServletRequest req) {
		Map<String, Object> attributes = 
			    new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
		Enumeration<String> en = req.getHeaderNames();
		while (en.hasMoreElements()) {
			String headerName = en.nextElement();
			String headerValue = req.getHeader(headerName);
			attributes.put(headerName, headerValue);
		}
		return Collections.unmodifiableMap(attributes);
	}
	
	private void removeRunningScript(String uuid) {
		runningReactiveScripts.complete(uuid);;
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = UUID.randomUUID().toString();
		String cancel = request.getParameter("cancel");
		if(cancel!=null) {
			cancel(cancel,request,response);
		}
		
		if(request.getParameter("list")!=null) {
		    
			listScriptsHtml(request,response);
			return;
		}
		String serviceHeader = request.getHeader("X-Navajo-Service");
		if(serviceHeader==null) {
		    throw new NullPointerException("Missing service header. Streaming Endpoint requires a 'X-Navajo-Service' header");
		}
		AsyncContext ac = request.startAsync();
		ResponseSubscriber responseSubscriber = new ResponseSubscriber(ac);
		String debugString = request.getHeader("X-Navajo-Debug");
		Optional<String> responseEncoding = decideEncoding(request.getHeader("Accept-Encoding"));
		boolean debug = debugString != null;
		ReactiveScript rs;
		try {
			rs = buildScript(serviceHeader,debug);
			if(rs.binaryMimeType().isPresent()) {
				response.addHeader("Content-Type", rs.binaryMimeType().get());
			} else {
				response.addHeader("Content-Type", "text/xml;charset=utf-8");
			}
			if(responseEncoding.isPresent()) {
				response.addHeader("Content-Encoding", responseEncoding.get());
			}
		} catch (ReactiveParseException e2) {
			respondError("Script compilation problem",serviceHeader,uuid, responseEncoding,response,  responseSubscriber, e2);	
			return;
		}
		ac.setTimeout(-1);
		Single<StreamScriptContext> context;
		try {
			context = createScriptContext(uuid, ac, responseSubscriber,rs.streamInput());
		} catch (AuthorizationException e3) {
			logger.error("Authorization problem: ",e3);
			response.sendError(401,"Not authorized");
			return;
		} catch (Throwable e3) {
			logger.error("Low level problem: ",e3);
			// TODO do something prettier?
			response.sendError(500,"Server error");
			return;
		}
//		runningReactiveScripts.submit(context);
		
			
		try {


			// TODO Cache file exists result + Flush on change
			Function<? super Throwable,? extends Publisher<? extends DataItem>> cc = e->{
				logger.error("Error detected: {}",e);
				return errorMessage(true,serviceHeader,Optional.of(e), e.getMessage()).map(DataItem::of);
			};

//			if(!rs.streamInput()) {			}

			Flowable<DataItem> execution = context
					.map(ctx->rs.execute(ctx))
					.toFlowable().flatMap(e->e).concatMapEager(e->e)
					.doOnComplete(()->ac.complete())
					.doOnCancel(()->ac.complete())
	                .doOnError((e)->context.error(e))
	                .doOnCancel(()->removeRunningScript(uuid));
						
//					: context.map(ctx->rs.execute(ctx)).toFlowable().
//						
//						context.collect()
//						.map(n->context.withInputNavajo(n))
//						.map(ctx->rs.execute(ctx)
//								.concatMapEager(e->e)
//								)
//						.toFlowable()
//						.flatMap(elt->elt)
//						.doOnComplete(()->context.complete())
//		                .doOnError((e)->context.error(e))
//		                .doOnCancel(()->removeRunningScript(uuid));
//
			System.err.println(">>> DataType: "+rs.dataType());
			switch(rs.dataType()) {
			case DATA:
				execution
					.map(di->di.data())
					.compose(StreamCompress.compress(responseEncoding))
					.map(ByteBuffer::wrap)
					.subscribe(responseSubscriber);
				break;
			case SINGLEMESSAGE:
			case MESSAGE:
				execution
				.onErrorResumeNext(cc)
				.map(di->di.message())
				.map(msg->msg.toFlatString(ImmutableFactory.getInstance()).getBytes())
				.map(ByteBuffer::wrap)
				.subscribe(responseSubscriber);
				break;
			case EVENTSTREAM:
				execution
				.onErrorResumeNext(cc)
				.map(e->e.eventStream())
				.concatMap(e->e)
				.doOnCancel(()->System.err.println("Cancel at toplevel"))
				.lift(StreamDocument.filterMessageIgnore())
				.lift(StreamDocument.serialize())
				.compose(StreamCompress.compress(responseEncoding))
				.map(ByteBuffer::wrap)
				.subscribe(responseSubscriber);
				break;
			case EVENT:
				execution
					.onErrorResumeNext(cc)
					.map(di->di.event())
					.compose(StreamDocument.inNavajo(serviceHeader, Optional.empty(), Optional.empty(),rs.methods()))
					.lift(StreamDocument.filterMessageIgnore())
					.lift(StreamDocument.serialize())
					.compose(StreamCompress.compress(responseEncoding))
					.onErrorResumeNext(new Function<Throwable, Publisher<? extends byte[]>>() {
						@Override
						public Publisher<? extends byte[]> apply(Throwable e1) throws Exception {
							return errorMessage(true,serviceHeader, Optional.of(e1), e1.getMessage())
							.lift(StreamDocument.serialize())
							.compose(StreamCompress.compress(responseEncoding));
						}
					})
                    .doOnCancel(()->{logger.warn("AAA"); ac.complete();})
					.map(ByteBuffer::wrap)
					.subscribe(responseSubscriber);
				break;
			case EMPTY:
			case MSGSTREAM:
			case ANY:
				throw new UnsupportedOperationException("Can't deal with type: "+rs.dataType()+" on toplevel");
			case MSGLIST:
				break;
			default:
				break;

			}
		} catch (Throwable e1) {
			respondError("General error", serviceHeader,uuid, responseEncoding, response, responseSubscriber, e1);
			return;
		}
	}


	private void cancel(String cancel, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		runningReactiveScripts.cancel(cancel);
//		objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, r);
		response.sendRedirect("/stream?list");
//		listScriptsHtml(request, response);
		
	}

	private void listScriptsHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.write("<html><head></head><body>");
		writer.write("<h2>Running scripts:</h2><ul>");
		runningReactiveScripts.contexts().forEach(e->{
			writer.write("<li><a href=\"stream?cancel="+e.uuid()+"&list\">"+e.getService()+"</li>");
		});
		writer.write("<ul></body></html>");
	}
//	private void listScriptsJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("application/json");
//		PrintWriter writer = response.getWriter();
//
//		objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, runningReactiveScripts.asJson());
//	}

	private void respondError(String message, String service, String uuid, Optional<String> responseEncoding, HttpServletResponse response,
			Subscriber<ByteBuffer> responseSubscriber, Throwable e1) {
		logger.error("Returning error with message: "+message+" with responseEncoding: "+responseEncoding,e1);
		response.addHeader("Content-Type", "text/xml;charset=utf-8");
		if(responseEncoding.isPresent()) {
			response.addHeader("Content-Encoding", responseEncoding.get());
		}
		errorMessage(true,service, Optional.of(e1), message)
			.lift(StreamDocument.serialize())
			.compose(StreamCompress.compress(responseEncoding))
			.doOnTerminate(()->removeRunningScript(uuid))
			.map(ByteBuffer::wrap)
			.subscribe(responseSubscriber);
	}


	private ReactiveScript buildScript(String service, boolean debug) throws IOException {
		return reactiveScriptEnvironment.build(service, debug);

	}

	public Access authenticate(String service, String authHeader, String tenant) throws AuthorizationException {
	    if(tenant==null) {
            throw new AuthorizationException(true, false, "placeholder", "Can not authenticate without tenant!");
        }
	    
        Access a = new Access(-1, -1, "placeholder", service, "stream", "ip", "hostname", null, false, null);
		
		a.setTenant(tenant);
		a.setInDoc(NavajoFactory.getInstance().createNavajo());
		authMethodBuilder.getInstanceForRequest(authHeader).process(a);
		return a;
	}

	private static Flowable<NavajoStreamEvent> errorMessage(boolean wrapNavajo, String service, Optional<Throwable> throwable, String message) {

		Flowable<NavajoStreamEvent> result = Msg.create("error")
				.with(Prop.create("code","-1","integer" ))
				.with(Prop.create("description", message))
				.with(Prop.create("service", service))
				.with(Prop.create("exception", throwable.isPresent()?throwable.get().getMessage():""))
				.stream()
//				.concatWith(Observable.just(Events.done()))
				.toFlowable(BackpressureStrategy.BUFFER);
		if(wrapNavajo) {
			result = result.compose(StreamDocument.inNavajo(service, Optional.empty(),Optional.of("")));
		}
		return result;
	}
	
	public static Optional<String> decideEncoding(String accept) {
		if (accept == null) {
			return Optional.empty();
		}
		String[] encodings = accept.split(",");
		Set<String> acceptedEncodings = new HashSet<>();
		for (String encoding : encodings) {
			acceptedEncodings.add(encoding.trim());
		}
		if (acceptedEncodings.contains("deflate")) {
			return Optional.of("deflate");
		}
		if (acceptedEncodings.contains("jzlib")) {
			return Optional.of("jzlib");
		}
		if (acceptedEncodings.contains("gzip")) {
			return Optional.of("gzip");
		}
		return Optional.empty();
	}
	
    private static byte[] getByteArrayFromByteBuffer(ByteBuffer byteBuffer) {
		    byte[] bytesArray = new byte[byteBuffer.remaining()];
		    byteBuffer.get(bytesArray, 0, bytesArray.length);
		    return bytesArray;
	}

	private Single<StreamScriptContext> createScriptContext(String uuid, AsyncContext ac, Disposable responseSubscriber, boolean streamInput) throws IOException, AuthorizationException {
		final HttpServletRequest req = (HttpServletRequest) ac.getRequest();
		Map<String, Object> attributes = extractHeaders(req);
		String tenant = determineTenantFromRequest(req);
		String serviceHeader = (String) attributes.get("X-Navajo-Service");
		String authorizationHeader = (String) attributes.get("Authorization");
		
		if(authorizationHeader == null || authorizationHeader.trim().equals("")) {
			throw new NullPointerException("Missing authorizationHeader header. Streaming Endpoint requires a 'Authorization' header");
		}
		if(tenant == null) {
			throw new NullPointerException("Missing tenant header. Streaming Endpoint requires a tenant");
		}
		Access access = authenticate(serviceHeader, authorizationHeader, tenant);
		if("GET".equals(req.getMethod())) {
			Navajo in = NavajoFactory.getInstance().createNavajo();
			in.addHeader(NavajoFactory.getInstance().createHeader(in, serviceHeader, null, null, -1));
			return Single.just(new StreamScriptContext(tenant,
					serviceHeader,
					in,
					attributes,
					Optional.empty(),
					Optional.of((ReactiveScriptRunner)this.reactiveScriptEnvironment), 
					Collections.emptyList(), 
					Optional.of(()->{responseSubscriber.dispose(); ac.complete();}),
					Optional.ofNullable(this.runningReactiveScripts),
					Optional.empty()
				));
		}
		String requestEncoding = (String) attributes.get("Content-Encoding");
		RequestPublisher rp = new RequestPublisher(ac, 8192);
		Flowable<NavajoStreamEvent> inputStream = Flowable.fromPublisher(rp)
			.map(e->getByteArrayFromByteBuffer(e))
			.compose(StreamCompress.decompress(Optional.ofNullable(requestEncoding)))
			.lift(XML.parseFlowable(50))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e);
		
		
		
		if(streamInput) {
			Flowable<DataItem> input = inputStream
					.map(DataItem::of);
			return Single.just(
					new StreamScriptContext(tenant,serviceHeader,NavajoFactory.getInstance().createNavajo(), 
							attributes, 
							Optional.of(input),Optional.of(this.reactiveScriptEnvironment),
							Collections.emptyList(), Optional.of(() -> {responseSubscriber.dispose(); ac.complete();}), 
							Optional.of(runningReactiveScripts),
							Optional.of(access.getInDoc())));
		} else {
			Single<Navajo> inputNavajo = inputStream
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
				.firstOrError();
			
			return inputNavajo.map(nav->{
				return new StreamScriptContext(tenant,serviceHeader,nav, attributes, Optional.empty(), Optional.of(this.reactiveScriptEnvironment),
		                Collections.emptyList(), Optional.of(() -> {
		                    responseSubscriber.dispose();
		                    ac.complete();
		                }), Optional.of(runningReactiveScripts), Optional.of(access.getInDoc()));
			});
		}
		
		
//	au
	}

	// warn: Duplicated code
	private static String determineTenantFromRequest(final HttpServletRequest req) {
		String requestInstance = req.getHeader("X-Navajo-Instance");
		if (requestInstance != null) {
			return requestInstance;
		}
		String pathinfo = req.getPathInfo();
		if(pathinfo==null) {
			return null;
		}
		if (pathinfo.length() > 0 && pathinfo.charAt(0) == '/') {
			pathinfo = pathinfo.substring(1);
		}
		String instance = null;
		if (pathinfo.indexOf('/') != -1) {
			instance = pathinfo.substring(0, pathinfo.indexOf('/'));
		} else {
			instance = pathinfo;
		}
		return instance;
	}
}
