package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.Collections;
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

import com.dexels.navajo.authentication.api.AuthenticationMethodBuilder;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import nl.codemonkey.reactiveservlet.Servlets;

public class NonBlockingListener extends HttpServlet {
	private static final long serialVersionUID = -4381216748627396838L;

	private LocalClient localClient;
	private final Map<String,Script> scripts = new HashMap<>();
	private final Map<String,SimpleScript> simpleScripts = new HashMap<>();
				
	private final static Logger logger = LoggerFactory.getLogger(NonBlockingListener.class);

	private AuthenticationMethodBuilder authMethodBuilder;
	
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

	private static Flowable<NavajoStreamEvent> emptyDocument(StreamScriptContext context) {
		return Flowable.just(
				Events.started(NavajoHead.createSimple(context.service, context.username, context.password))
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
		return Collections.unmodifiableMap(attributes);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			boolean isGet = "GET".equals(request.getMethod());
			AsyncContext ac = request.startAsync();
			ac.setTimeout(3600000);
			StreamScriptContext context = determineContextFromRequest(request);
			String requestEncoding = (String) context.attributes.get("Content-Encoding");
			String responseEncoding = decideEncoding(request.getHeader("Accept-Encoding"));
			System.err.println("Tenant determined: "+context.tenant+" service: "+context.service);

			try {
				authenticate(context, (String)context.attributes.get("Authorization"));
			} catch (Exception e1) {
				errorMessage(context.service, context.username, -1, e1.getMessage())
				.lift(StreamDocument.serialize())
				.compose(StreamDocument.compress(responseEncoding))
				.subscribe(Servlets.createSubscriber(ac));
				return;
			}
			Flowable<NavajoStreamEvent> input = isGet ? emptyDocument(context) : 
				Servlets.createFlowable(ac, 1000)
				.observeOn(Schedulers.io(),false,10)
				.compose(StreamDocument.decompress2(requestEncoding))
				.lift(XML.parseFlowable(10))
				.flatMap(e->e)
				.lift(StreamDocument.parse())
				.concatMap(e->e);
			
			context.setInputFlowable(input);

			processStreamingScript(request,input)
				.lift(StreamDocument.filterMessageIgnore())
				.lift(StreamDocument.serialize())
				.compose(StreamDocument.compress(responseEncoding))
				.subscribe(Servlets.createSubscriber(ac));
		} catch (Exception e1) {
			throw new IOException("Servlet problem", e1);
		}

	}

	private Flowable<NavajoStreamEvent> processStreamingScript(HttpServletRequest request,Flowable<NavajoStreamEvent> eventStream) throws Exception {
		StreamScriptContext context = determineContextFromRequest(request);

		System.err.println("Tenant determined: "+context.tenant+" service: "+context.service);
		if(context.service ==null) {
			return eventStream
					.lift(StreamDocument.collectFlowable())
					.flatMap(inputNav->executeLegacy(context.service, context.username, context.tenant, inputNav));
		}
		Script s = scripts.get(context.service);
		if(s!=null) {
			return eventStream
					.compose(s.call(context));
		}
		SimpleScript ss = simpleScripts.get(context.service);
		if(ss!=null) {
			return eventStream
					.lift(StreamDocument.collectFlowable())
					.compose(ss.apply(context))
					.compose(StreamDocument.inNavajo(context.service, context.username, ""));
		}
		return eventStream
				.lift(StreamDocument.collectFlowable())
				.flatMap(inputNav->executeLegacy(context.service, context.username, context.tenant, inputNav));
//				.compose(StreamDocument.inNavajo(context.service, context.username, ""));
	}
	

	public void authenticate(StreamScriptContext context, String authHeader) throws AuthorizationException {
		Access a = new Access(-1,-1,context.username,context.service,"stream","ip","hostname",null,false,"access");
		authMethodBuilder.getInstanceForRequest(authHeader).process(a);
	}

//	private FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent> createTransformerScript(StreamScriptContext context) {
//		Script s = scripts.get(context.service);
//		if(s!=null) {
//			return s.call(context);
//		}
//		SimpleScript ss = simpleScripts.get(context.service);
//		if(ss!=null) {
//			
//			return ss.apply(context);
//		}
//		return s.call(context);
//	}

	private static Flowable<NavajoStreamEvent> errorMessage(String service, String user, int code, String message) {
		return Msg.create("error")
				.with(Prop.create("code",code))
				.with(Prop.create("description", message))
				.stream()
				.toFlowable(BackpressureStrategy.BUFFER)
				.compose(StreamDocument.inNavajo(service, user, ""));
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

	private final Flowable<NavajoStreamEvent> executeLegacy(String service, String user, String tenant,Navajo in) {
		Navajo result;
		try {
			result = execute(tenant, in);
		} catch (Throwable e) {
			logger.error("Error: ", e);
			return errorMessage(service,user,101,"Could not resolve script: "+service);
		}
		return Observable.just(result)
			.lift(StreamDocument.domStream())
			.toFlowable(BackpressureStrategy.BUFFER);
	}
	
	private final Navajo execute(String tenant, Navajo in) throws IOException, ServletException {

		if (tenant != null) {
			MDC.put("instance", tenant);
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

	private static StreamScriptContext  determineContextFromRequest(final HttpServletRequest req) {
		String username = req.getHeader("X-Navajo-Username");
		String password = req.getHeader("X-Navajo-Password");
		String tenant = determineTenantFromRequest(req);
		Map<String, Object> attributes = extractHeaders(req);
		String serviceHeader = req.getHeader("X-Navajo-Service");

		return new StreamScriptContext(tenant,serviceHeader, username,password,attributes);
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
