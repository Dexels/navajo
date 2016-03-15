package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jitu.rx.servlet.ObservableServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.io.ObservableStreams;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class NonBlockingListener extends HttpServlet {
	private static final long serialVersionUID = -4381216748627396838L;
	private LocalClient localClient;
//	private Script script;
	private final Map<String,Script> scripts = new HashMap<>();
	private final Map<String,SimpleScript> simpleScripts = new HashMap<>();
				
	private final static Logger logger = LoggerFactory.getLogger(NonBlockingListener.class);
	private static final int INPUT_BUFFER_SIZE = 1024;

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
		Map<String, Object> attributes = extractHeaders(req);
		String tenant = determineTenantFromRequest(req);

		String navajoService = (String) attributes.get("X-Navajo-Service");
		if(navajoService!=null) {
			processStreamingScript(tenant,navajoService,attributes,ac,null);
			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext ac = req.startAsync();
		Map<String, Object> attributes = extractHeaders(req);
		String tenant = determineTenantFromRequest(req);
		ServletInputStream in = req.getInputStream();

		String navajoService = (String) attributes.get("X-Navajo-Service");
		String responseEncoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		if (responseEncoding != null) {
			resp.addHeader("Content-Encoding", responseEncoding);
		}
		if(navajoService!=null) {
			processStreamingScript(tenant,navajoService,attributes,ac,responseEncoding);
			return;
		}
		String contentEncoding = (String) attributes.get("Content-Encoding");
		ObservableStreams.streamInputStreamWithBufferSize(in,INPUT_BUFFER_SIZE)
				.lift(NavajoStreamOperators.decompress(contentEncoding))
				.lift(XML.parse())
				.lift(NAVADOC.parse(attributes))
				.lift(NAVADOC.collect(attributes))
				.flatMap(inputNav->executeLegacy(navajoService, tenant, inputNav))
				.lift(NAVADOC.serialize())
				.lift(NavajoStreamOperators.compress(responseEncoding))
				.subscribe(createOutput(ac));
	}

	private Map<String, Object> extractHeaders(HttpServletRequest req) {
		Map<String, Object> attributes = new HashMap<>();
		Enumeration<String> en = req.getHeaderNames();
		while (en.hasMoreElements()) {
			String headerName = en.nextElement();
			String headerValue = req.getHeader(headerName);
			attributes.put(headerName, headerValue);
		}
		return attributes;
	}

	private void processStreamingScript(String tenant,String navajoService, Map<String, Object> attributes, AsyncContext asyncContext, String responseEncoding) throws IOException {
		ServletInputStream in = asyncContext.getRequest().getInputStream();
		String contentEncoding = (String) attributes.get("Content-Encoding");
		Observer<byte[]> output = createOutput(asyncContext);
		SimpleScript simple = simpleScripts.get(navajoService);
		if(simple!=null) {
			ObservableStreams.streamInputStreamWithBufferSize(in, INPUT_BUFFER_SIZE)
			.lift(NavajoStreamOperators.decompress(contentEncoding))
			.lift(XML.parse())
			.lift(NAVADOC.parse(attributes))
			.lift(NAVADOC.collect(attributes))
			.flatMap(simple::call)
			.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")))
			.lift(NAVADOC.serialize())
			.lift(NavajoStreamOperators.compress(responseEncoding))
			.subscribe(output);
			return;
		}
		Script s = scripts.get(navajoService);
		if(s==null) {
			logger.info("Script unresolved.");

			return;
		}
		ObservableStreams.streamInputStreamWithBufferSize(in, INPUT_BUFFER_SIZE)
			.lift(NavajoStreamOperators.decompress(contentEncoding))
			.lift(XML.parse())
			.lift(NAVADOC.parse(attributes))
			.compose(s)
			.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")))
			.lift(NAVADOC.serialize())
			.lift(NavajoStreamOperators.compress(responseEncoding))
			.subscribe(output);
	}

	private Observable<NavajoStreamEvent> errorMessage(Navajo in) {
		return Msg.create()
				.with(Prop.create("code",101))
				.with(Prop.create("description", "Could not resolve script: "+in.getHeader().getRPCName()))
				.stream()
				.compose(NavajoStreamOperators.inNavajo(in.getHeader().getRPCName(), in.getHeader().getRPCUser(), ""));
	}


	public String decideEncoding(String accept) {
		if (accept == null) {
			return null;
		}
		String[] encodings = accept.split(",");
		Set<String> acceptedEncodings = new HashSet<>();
		for (String encoding : encodings) {
			acceptedEncodings.add(encoding);
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
			// TODO Embed throwable in message?
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
				// String queueId = myQueue.getId();
				// int queueLength = myQueue.getQueueSize();

				// ClientInfo clientInfo = getRequest().createClientInfo(
				// scheduledAt, startedAt, queueLength, queueId);
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
	private String determineTenantFromRequest(final HttpServletRequest req) {
		String requestInstance = req.getHeader("X-Navajo-Instance");
		if (requestInstance != null) {
			return requestInstance;
		}
		String pathinfo = req.getPathInfo();
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

	static class ReadObserver implements Observer<ByteBuffer> {
		private final HttpServletResponse resp;
		private final AsyncContext ac;

		ReadObserver(HttpServletResponse resp, AsyncContext ac) {
			this.resp = resp;
			this.ac = ac;
		}

		@Override
		public void onCompleted() {
			System.out.println("Read onCompleted=" + Thread.currentThread());
			resp.setStatus(HttpServletResponse.SC_OK);

			Observable<ByteBuffer> data = data();
			ServletOutputStream out = null;
			try {
				out = resp.getOutputStream();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			Observable<Void> writeStatus = ObservableServlet.write(data, out);
			writeStatus.subscribe(new WriteObserver(ac));
		}

		@Override
		public void onError(Throwable e) {
			System.out.println("read onError=" + Thread.currentThread());
			e.printStackTrace();
		}

		@Override
		public void onNext(ByteBuffer buf) {
			// Thread.dumpStack();
			// System.out.println("read onNext=" + Thread.currentThread());
		}

		Observable<ByteBuffer> data() {
			ByteBuffer[] data = new ByteBuffer[1000000];
			for (int i = 0; i < data.length; i++) {
				data[i] = ByteBuffer.wrap((i + "0000000000000\n").getBytes());
			}
			return Observable.from(data);
		}
	}

	// Rewrite to passing HttpResponse to create the outputstream on demand?
//	private Observer<NavajoStreamEvent> createOutput(OutputStream out) {
//		PublishSubject<NavajoStreamEvent> subject = PublishSubject.<NavajoStreamEvent>create();
//		subject.lift(NAVADOC.serialize()).subscribe(
//		b->{
//			try {
//				out.write(b);
//			} catch (Exception e) {
//				logger.error("Error: ", e);
//			}
//		}
//			,t->logger.error("Error: ", t)
//			,()->{
//				try {
//					out.flush(); 
//					out.close();
//				} catch (Exception e) {
//					logger.error("Error: ", e);
//				}
//			}
//		);
//		
//		return subject;
//	}
//	
	private Subscriber<byte[]> createOutput(AsyncContext context) throws IOException {
		OutputStream out = context.getResponse().getOutputStream();
		return new Subscriber<byte[]>(){
			@Override
			public void onCompleted() {
				try {
					out.flush(); 
					context.complete();
					out.close();
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public void onError(Throwable ex) {
					logger.error("Error!",ex);
					HttpServletResponse r = (HttpServletResponse) context.getResponse();
					try {
						r.sendError(500,"Trouble");
						context.complete();
					} catch (IOException e) {

					}
					
			}

			@Override
			public void onNext(byte[] b) {
				try {
					out.write(b);
				} catch (Exception e) {
					logger.error("Error: ", e);
					onError(e);
					unsubscribe();
				}
				
			}};

	}
	static class WriteObserver implements Observer<Void> {
		private final AsyncContext ac;

		public WriteObserver(AsyncContext ac) {
			this.ac = ac;
		}

		@Override
		public void onCompleted() {
			System.out.println("Composite Write onCompleted");
			ac.complete();
		}

		@Override
		public void onError(Throwable e) {
			System.out.println("write onError=" + Thread.currentThread());
			e.printStackTrace();
		}

		@Override
		public void onNext(Void args) {
			// System.out.println("Composite Write onNext");
			// no-op
		}
	}

}
