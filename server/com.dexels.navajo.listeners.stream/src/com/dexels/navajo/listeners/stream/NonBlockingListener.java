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
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jitu.rx.servlet.ObservableServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.api.SimpleScript;
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
			processStreamingScript(navajoService,attributes,ac);
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
		if(navajoService!=null) {
			processStreamingScript(navajoService,attributes,ac);
			return;
		}
		
		Navajo inputNavajo = ObservableStreams.streamInputStreamWithBufferSize(in, 1024)
				.lift(XML.parse())
				.lift(NAVADOC.parse(attributes))
				.lift(NAVADOC.collect(attributes))
				.toBlocking()
				.first();

		Navajo outDoc = execute(tenant, inputNavajo); 
		String encoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		if (encoding != null) {
			resp.addHeader("Content-Encoding", encoding);
		}
		// System.err.println("Starting output");
		Observable.just(outDoc).lift(NAVADOC.stream()).lift(NAVADOC.serialize())
				// .lift(NavajoStreamOperators.compress(encoding))
				.subscribe(bytes -> {
					// System.err.println("Writing output: "+bytes.length);
					writeResponse(resp, bytes);
				} , err -> {
					try {
						resp.sendError(501);
					} catch (Exception e1) {
						logger.error("Error: ", e1);
					}
				} , () -> {
					ac.complete();
					try {
						resp.getOutputStream().close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
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

	private void processStreamingScript(String navajoService, Map<String, Object> attributes, AsyncContext asyncContext) throws IOException {
		ServletInputStream in = asyncContext.getRequest().getInputStream();
		Observer<byte[]> output = createOutput(asyncContext.getResponse(),asyncContext);
		SimpleScript simple = simpleScripts.get(navajoService);
		if(simple!=null) {
			Navajo input = ObservableStreams.streamInputStreamWithBufferSize(in, INPUT_BUFFER_SIZE)
			.lift(XML.parse())
			.lift(NAVADOC.parse(attributes))
			.lift(NAVADOC.collect(attributes))
			.toBlocking()
			.first();
			simple.call(input)
			.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_usr")))
			.lift(NAVADOC.serialize())
			.subscribe(output);
			return;
		}
		Script s = scripts.get(navajoService);
		if(s==null) {
			logger.info("Script unresolved. TODO: Write pretty erroro message");
		}
//		s.create(runtime);
		ObservableStreams.streamInputStreamWithBufferSize(in, INPUT_BUFFER_SIZE)
			.lift(XML.parse())
			.lift(NAVADOC.parse(attributes))
			.compose(s)
			.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_usr")))
			.lift(NAVADOC.serialize())
			.subscribe(output);
	}


	private Observable<ByteBuffer> decompress(Observable<ByteBuffer> input, Map<String, Object> attributes) {
		String encoding = (String) attributes.get("Content-Encoding");
		if (encoding == null) {
			return input;
		}
		if (encoding.equals("jzlib")) {
			return input.lift(NavajoStreamOperators.inflate());
		}
		return input;
	}

	private void writeResponse(final HttpServletResponse resp, byte[] bytes) {
		try {
			resp.getOutputStream().write(bytes);
		} catch (IOException e) {
			logger.error("Error: ", e);
			try {
				resp.sendError(502);
			} catch (IOException e1) {
				logger.error("Error: ", e1);
			}
		}
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
//
//	private Subscription sendNavajo(Navajo n, OutputStream out) {
//		NavajoStreamSerializer nss = new NavajoStreamSerializer();
//		return Observable.<Navajo> just(n).flatMap(NavajoDomStreamer::feed).flatMap(nss::feed)
//				.subscribe(b -> write(out, b));
//	}

//	private void write(OutputStream out, byte[] b) {
//		try {
//			out.write(b);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

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
	private Subscriber<byte[]> createOutput(ServletResponse response, AsyncContext context) throws IOException {
		OutputStream out = response.getOutputStream();
		return new Subscriber<byte[]>(){
//			File outfile = new File("/Users/frank/outputxx.xml");
//			OutputStream outputStream = new FileOutputStream(outfile);
			@Override
			public void onCompleted() {
				try {
					out.flush(); 
					context.complete();
					out.close();
//					outputStream.flush();
//					outputStream.close();
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public void onError(Throwable ex) {
					logger.error("Error!",ex);
					
			}

			@Override
			public void onNext(byte[] b) {
				try {
					out.write(b);
//					out.flush();
//					outputStream.write(b);
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
