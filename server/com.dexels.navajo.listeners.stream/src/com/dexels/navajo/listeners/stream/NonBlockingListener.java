package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import nl.codemonkey.reactiveservlet.Servlets;

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
		Flowable<NavajoStreamEvent> emptyInput = emptyDocument(navajoService,"","");
		
		Map<String, Object> attributes = extractHeaders(req);
		processStreamingScript(req,navajoService,emptyInput,attributes,ac,responseEncoding)
			.lift(StreamDocument.filterMessageIgnore())
			.lift(StreamDocument.serialize())
			.compose(StreamDocument.compress(responseEncoding))
			.subscribe(Servlets.createSubscriber(ac));
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
		
		Flowable<NavajoStreamEvent> eventStream = Servlets.createFlowable(ac, 1000)
			.observeOn(Schedulers.io(),false,10)
			.compose(StreamDocument.decompress2(requestEncoding))
			.lift(XML.parseFlowable(10))
			.flatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e);
			

		processStreamingScript(req,navajoService,eventStream,attributes,ac,responseEncoding)
			.lift(StreamDocument.filterMessageIgnore())
			.lift(StreamDocument.serialize())
			.compose(StreamDocument.compress(responseEncoding))
			.subscribe(Servlets.createSubscriber(ac));
	}


	private static Flowable<NavajoStreamEvent> emptyDocument(String service, String username, String password) {
		return Flowable.just(
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

	
	private Flowable<NavajoStreamEvent> processStreamingScript(HttpServletRequest request,String navajoService,Flowable<NavajoStreamEvent> eventStream, Map<String, Object> attributes, AsyncContext asyncContext, String responseEncoding) throws IOException {
		String tenant = determineTenantFromRequest(request);
		System.err.println("Tenant determined: "+tenant+" service: "+navajoService);
		if(tenant==null) {
		}
		if(navajoService ==null) {
			return eventStream
					.lift(StreamDocument.collectFlowable())
					.flatMap(inputNav->executeLegacy(navajoService, tenant, inputNav));
		}
		SimpleScript simple = simpleScripts.get(navajoService);
		if(simple!=null) {
			return eventStream
				.lift(StreamDocument.collectFlowable())
				.flatMap(simple::call)
				.compose(StreamDocument.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));
		}
		Script s = scripts.get(navajoService);
		if(s!=null) {
			return s.call(eventStream)
					.compose(StreamDocument.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));
		}
		logger.debug("Script unresolved.");
		return emptyDocument(navajoService, "", "");

	}

	private static Flowable<NavajoStreamEvent> errorMessage(Navajo in) {
		return Msg.create()
				.with(Prop.create("code",101))
				.with(Prop.create("description", "Could not resolve script: "+in.getHeader().getRPCName()))
				.stream()
				.toFlowable(BackpressureStrategy.BUFFER)
				.compose(StreamDocument.inNavajo(in.getHeader().getRPCName(), in.getHeader().getRPCUser(), ""));
	}
	
	
//	public SingleTransformer<NavajoStreamEvent, Navajo> executeLegacy() {
//		return new SingleTransformer<NavajoStreamEvent, Navajo>() {
//
//			@Override
//			public SingleSource<Navajo> apply(Single<NavajoStreamEvent> in) {
//				return new SingleSource<Navajo>() {
//
//					@Override
//					public void subscribe(SingleObserver<? super Navajo> n) {
//						n.
//					}
//				};
//			}
//		};
//		
//	}


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

	private final Flowable<NavajoStreamEvent> executeLegacy(String navajoService, String tenant,Navajo in) {
		Navajo result;
		try {
			result = execute(tenant, in);
		} catch (Throwable e) {
			logger.error("Error: ", e);
			return errorMessage(in);
		}
		return Observable.just(result)
			.lift(StreamDocument.domStream())
			.toFlowable(BackpressureStrategy.BUFFER);
	}
	
	
	
//	public FlowableTransformer<NavajoStreamEvent,Navajo> executeLegacy(String navajoService, String tenant,Navajo in) {
//		return new FlowableTransformer<NavajoStreamEvent, Navajo>() {
//
//			@Override
//			public Publisher<Navajo> apply(Flowable<NavajoStreamEvent> arg0) {
//				return executeLegacy(navajoService, tenant, in);
//			}
//		};
//		
//	}
	
	
	
	
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
}
