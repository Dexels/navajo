package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class NonBlockingListener extends HttpServlet {
//	private static final int BACKPRESSURE_LIMIT = 1000000;
	private static final long serialVersionUID = -4381216748627396838L;
	private LocalClient localClient;
//	private Script script;
	private final Map<String,Script> scripts = new HashMap<>();
	private final Map<String,SimpleScript> simpleScripts = new HashMap<>();
				
	private final static Logger logger = LoggerFactory.getLogger(NonBlockingListener.class);

	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}

	public void addScript(Script script,Map<String,Object> settings) {
		String name = (String) settings.get("navajo.scriptName");
		if(name!=null) {
			scripts.put(name, script);
		}
	}
	
	public void removeScript(Script script,Map<String,Object> settings) {
		String name = (String) settings.get("navajo.scriptName");
		if(name!=null) {
			scripts.remove(name);
		}
	}
	
	public void addSimpleScript(SimpleScript script,Map<String,Object> settings) {
		String name = (String) settings.get("navajo.scriptName");
		if(name!=null) {
			simpleScripts.put(name, script);
		}
	}
	
	public void removeSimpleScript(SimpleScript script,Map<String,Object> settings) {
		String name = (String) settings.get("navajo.scriptName");
		if(name!=null) {
			simpleScripts.remove(name);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext ac = req.startAsync();
		
		String navajoService = determineService(req);
		String responseEncoding = decideEncoding(req.getHeader("Accept-Encoding"));
		
		if (responseEncoding != null) {
			resp.addHeader("Content-Encoding", responseEncoding);
		}
		Observable<NavajoStreamEvent> emptyInput = emptyDocument(navajoService,"","");
		
		Map<String, Object> attributes = extractHeaders(req);
		processStreamingScript(req,navajoService,emptyInput,attributes,ac,responseEncoding)
			.lift(NAVADOC.filterMessageIgnore())
			.lift(NAVADOC.serialize())
			.lift(NavajoStreamOperators.compress(responseEncoding))
			.subscribe(createOutput(ac,"text/xml"));
		return;
	}

	private static String determineService(HttpServletRequest req) {
		String serviceHeader = req.getHeader("X-Navajo-Service");
		if(serviceHeader!=null) {
			return serviceHeader;
		}
		String serviceParam = req.getParameter("service");
		if(serviceParam!=null) {
			return serviceParam;
		}
		return null;
	}
	
    public static Observable<byte[]> createReadListener(final ServletInputStream in) {
        return Observable.create(new OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                final ServletReadListener listener = new ServletReadListener(in, subscriber);
                in.setReadListener(listener);
            }
        });
    }
	
	@Override
	protected void doPost(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext ac = req.startAsync();
		Map<String, Object> attributes = extractHeaders(req);
		String navajoService = determineService(req);
		String requestEncoding = (String) attributes.get("Content-Encoding");
		String responseEncoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		if (responseEncoding != null) {
			resp.addHeader("Content-Encoding", responseEncoding);
		}
		
		Observable<NavajoStreamEvent> eventStream = createReadListener(ac.getRequest().getInputStream())
				.lift(NavajoStreamOperators.decompress(requestEncoding))
				.lift(XML.parse())
				.lift(NAVADOC.parse(attributes));	

		processStreamingScript(req,navajoService,eventStream,attributes,ac,responseEncoding)
				.lift(NAVADOC.filterMessageIgnore())
				.lift(NAVADOC.serialize())
				.lift(NavajoStreamOperators.compress(responseEncoding))
				.subscribe(createOutput(ac,"text/xml"));
			return;
//		parsePost(ac, attributes)
//		eventStream
//			.lift(NAVADOC.collect(attributes))
//			.flatMap(inputNav->executeLegacy(navajoService, tenant, inputNav))
//			.lift(NAVADOC.serialize())
//			.lift(NavajoStreamOperators.compress(responseEncoding))
//			.onBackpressureBuffer(BACKPRESSURE_LIMIT, ()->System.err.println("Overflow!!!!"))
//			.subscribe(createOutput(ac));
	}


	private static Observable<NavajoStreamEvent> emptyDocument(String service, String username, String password) {
		return Observable.just(
				Events.started(NavajoHead.createSimple(service, username, password))
				, Events.done()
				);
	}
	private static Map<String, Object> extractHeaders(HttpServletRequest req) {
		Map<String, Object> attributes = new HashMap<>();
		Enumeration<String> en = req.getHeaderNames();
		while (en.hasMoreElements()) {
			String headerName = en.nextElement();
			String headerValue = req.getHeader(headerName);
			attributes.put(headerName, headerValue);
		}
		return attributes;
	}

//	.lift(NAVADOC.serialize())
//	.lift(NavajoStreamOperators.compress(responseEncoding))
//	.subscribe(output);

	
	private Observable<NavajoStreamEvent> processStreamingScript(HttpServletRequest request,String navajoService,Observable<NavajoStreamEvent> eventStream, Map<String, Object> attributes, AsyncContext asyncContext, String responseEncoding) throws IOException {
//		ServletInputStream in = asyncContext.getRequest().getInputStream();
//		String contentEncoding = (String) attributes.get("Content-Encoding");
//		if("echo".equals(navajoService)) {
//			return eventStream.subscribeOn(Schedulers.io()).doOnNext(e->System.err.println("Event: "+e))			.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));
//		}
		String tenant = determineTenantFromRequest(request);

		if(tenant==null) {
			// generate error eent
			
		}
		if(navajoService ==null) {
			return eventStream
					.lift(NAVADOC.collect(attributes))
					.flatMap(inputNav->executeLegacy(navajoService, tenant, inputNav));

		}
		SimpleScript simple = simpleScripts.get(navajoService);
		if(simple!=null) {
			return eventStream
				.lift(NAVADOC.collect(attributes))
				.flatMap(simple::call)
				.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));
		}
		Script s = scripts.get(navajoService);
		if(s!=null) {
			return eventStream
					.compose(s)
					.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));
		}
		logger.debug("Script unresolved.");
		return null;

	}

	private static Observable<NavajoStreamEvent> errorMessage(Navajo in) {
		return Msg.create()
				.with(Prop.create("code",101))
				.with(Prop.create("description", "Could not resolve script: "+in.getHeader().getRPCName()))
				.stream()
				.compose(NavajoStreamOperators.inNavajo(in.getHeader().getRPCName(), in.getHeader().getRPCUser(), ""));
	}


	public static String decideEncoding(String accept) {
		if (accept == null) {
			return null;
		}
		String[] encodings = accept.split(",");
		Set<String> acceptedEncodings = new HashSet<>();
		for (String encoding : encodings) {
			acceptedEncodings.add(encoding.trim());
		}
		if (acceptedEncodings.contains("deflate")) {
			return "deflate";
		}
		if (acceptedEncodings.contains("jzlib")) {
			return "jzlib";
		}
		return null;
	}

	private final Observable<NavajoStreamEvent> executeLegacy(String navajoService, String tenant,Navajo in) {
		Navajo result;
		try {
			result = execute(tenant, in);
		} catch (Throwable e) {
			logger.error("Error: ", e);
			return errorMessage(in);
		}
		return Observable.just(result)
				.lift(NAVADOC.stream());
	}
	private final Navajo execute(String tenant, Navajo in) throws IOException, ServletException {

		// BufferedReader r = null;
		if (tenant != null) {
			MDC.put("instance", tenant);
			// myRequest.getInputDocument().getHeader().setHeaderAttribute("instance",
			// tenant);
		}
		try {
			in.getHeader().setHeaderAttribute("useComet", "true");
			if (in.getHeader().getHeaderAttribute("callback") != null) {

				String callback = in.getHeader().getHeaderAttribute("callback");

				Navajo callbackNavajo = getLocalClient().handleCallback(tenant, in, callback);
				return callbackNavajo;

			} else {
				Navajo outDoc = getLocalClient().handleInternal(tenant, in, null, null);
				return outDoc;
			}
		} catch (Throwable e) {
			if (e instanceof FatalException) {
				FatalException fe = (FatalException) e;
				if (fe.getMessage().equals("500.13")) {
					// Server too busy.
					throw new ServletException("500.13");
				}
			}
			throw new ServletException(e);
		} finally {
			MDC.remove("instance");
		}
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

	private static Subscriber<byte[]> createOutput(AsyncContext context, String contentType) throws IOException {
		context.getResponse().setContentType(contentType);
		final ServletOutputStream out = context.getResponse().getOutputStream();
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean completed = new AtomicBoolean(false);
		final Queue<byte[]> outputQueue = new ConcurrentLinkedQueue<byte[]>();
		out.setWriteListener(new WriteListener() {
			
			@Override
			public void onWritePossible() throws IOException {
				if(done.get() && !completed.get()) {
					logger.debug("Done switch trapped and write complete, closing context.");
					completed.compareAndSet(false, true);
					context.complete();
				}
				boolean ready = false;
				while(!outputQueue.isEmpty()) {
					byte[] element = outputQueue.poll();
					if(element!=null) {
						writeOutput(out, element);
						ready = isOutputReady(out);
					}
					if(!ready) {
						// according to: http://jetty.4.x6.nabble.com/jetty-users-jetty-9-1-ServletOutputStream-isReady-how-much-data-can-be-written-without-blocking-td4961171.html
						// not ready, will break loop and await new onWritePossible call
						return;
//					} else {
//						outputQueue.poll();
					}
				}
			}
			
			@Override
			public void onError(Throwable e) {
				logger.error("Error: ", e);
				// TODO Does not work with Async. Duh.
				context.complete();
			}
		});
		
		return new Subscriber<byte[]>(){

			
			@Override
			public void onCompleted() {
				try {
					logger.debug("Input completed. Setting done: "+done.get());
					done.compareAndSet(false,true);
					logger.debug("Write Done: "+done.get());
					if(isOutputReady(out)) {
						logger.debug("onCompleted done, and still ready, so closing context:");
						completed.compareAndSet(false, true);
						context.complete();
					} else {
						logger.debug("onCompleted done, but not ready, so deferring complete to onWritePossible");
					}
				
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public void onError(Throwable ex) {
					logger.error("Error!",ex);
					context.complete();
			}

			@Override
			public void onNext(byte[] b) {
				try {
					outputQueue.offer(b);
					boolean ready = true;
					while(ready && !outputQueue.isEmpty()) {
						ready = isOutputReady(out);
						if(!ready) {
							break;
						}
						byte[] element = outputQueue.poll();
						writeOutput(out, element);
					}

				} catch (Exception e) {
					logger.error("Error: ", e);
					onError(e);
					unsubscribe();
				}
				
			}
		};
	}

	
	private static boolean isOutputReady(ServletOutputStream out) {
		boolean ready = out.isReady();
		return ready;
	}

	private static void writeOutput(final ServletOutputStream out, byte[] element) throws IOException {
		out.write(element);
	}

	
}
