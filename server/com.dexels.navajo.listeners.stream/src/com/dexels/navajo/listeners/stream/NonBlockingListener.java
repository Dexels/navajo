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
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
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
import rx.Subscriber;

public class NonBlockingListener extends HttpServlet {
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
		Map<String, Object> attributes = extractHeaders(req);
		String tenant = determineTenantFromRequest(req);

		
		String navajoService = determineService(req, attributes);
		String responseEncoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		System.err.println("Accept encoding: "+ attributes.get("Accept-Encoding"));
		System.err.println("Response encoding: "+responseEncoding);
		
		if (responseEncoding != null) {
			resp.addHeader("Content-Encoding", responseEncoding);
		}
		if(navajoService!=null) {
			processStreamingScript(tenant,navajoService,emptyDocument(navajoService,"","",attributes),attributes,ac,responseEncoding);
			return;
		}
		emptyDocument(navajoService,"","",attributes)
			.lift(NAVADOC.collect(attributes))
			.flatMap(inputNav->executeLegacy(navajoService, tenant, inputNav))
			.lift(NAVADOC.serialize())
			.lift(NavajoStreamOperators.compress(responseEncoding))
			.subscribe(createOutput(ac));
	}

	private static String determineService(HttpServletRequest req, Map<String, Object> attributes) {
		String serviceHeader = (String) attributes.get("X-Navajo-Service");
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
		String tenant = determineTenantFromRequest(req);
		String navajoService = determineService(req, attributes);
		String responseEncoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		if (responseEncoding != null) {
			resp.addHeader("Content-Encoding", responseEncoding);
		}
		
		Observable<NavajoStreamEvent> eventStream = ObservableServlet.create(ac.getRequest().getInputStream())
				.lift(NavajoStreamOperators.decompress(responseEncoding))
				.lift(XML.parse())
				.lift(NAVADOC.parse(attributes));	
		
//		

		if(navajoService!=null) {
			processStreamingScript(tenant,navajoService,eventStream,attributes,ac,responseEncoding)
				.lift(NAVADOC.serialize())
				.lift(NavajoStreamOperators.compress(responseEncoding))
				.subscribe(createOutput(ac));
;
			return;
		}
//		parsePost(ac, attributes)
		eventStream
			.lift(NAVADOC.collect(attributes))
			.flatMap(inputNav->executeLegacy(navajoService, tenant, inputNav))
			.lift(NAVADOC.serialize())
			.lift(NavajoStreamOperators.compress(responseEncoding))
			.subscribe(createOutput(ac));
	}


	private static Observable<NavajoStreamEvent> emptyDocument(String service, String username, String password, Map<String, Object> attributes) {
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

	
	private Observable<NavajoStreamEvent> processStreamingScript(String tenant,String navajoService,Observable<NavajoStreamEvent> eventStream, Map<String, Object> attributes, AsyncContext asyncContext, String responseEncoding) throws IOException {
//		ServletInputStream in = asyncContext.getRequest().getInputStream();
//		String contentEncoding = (String) attributes.get("Content-Encoding");
		if("echo".equals(navajoService)) {
			return eventStream;
		}
		SimpleScript simple = simpleScripts.get(navajoService);
		if(simple!=null) {
			return eventStream
				.lift(NAVADOC.collect(attributes))
				.flatMap(simple::call)
				.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));
		}
		Script s = scripts.get(navajoService);
		if(s==null) {
			logger.info("Script unresolved.");
			// TODO
			return null;
		}
		return eventStream
			.compose(s)
			.compose(NavajoStreamOperators.inNavajo(navajoService, (String)attributes.get("rpc_usr"), (String)attributes.get("rpc_pwd")));

	}
	
	
//	public Operator<NavajoStreamEvent, NavajoStreamEvent> resolveScript(String navajoService) {
//		return new Operator<NavajoStreamEvent, NavajoStreamEvent>(){
//
//			@Override
//			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super NavajoStreamEvent> out) {
//				if("echo".equals(navajoService)) {
//					return out;
//				}
//				SimpleScript simple = simpleScripts.get(navajoService);
//				if(simple!=null) {
//					simple.c
//					return new Subscriber<NavajoStreamEvent>() {
//
//						@Override
//						public void onCompleted() {
//							out.onCompleted();
//						}
//
//						@Override
//						public void onError(Throwable ex) {
//							out.onError(ex);
//						}
//
//						@Override
//						public void onNext(NavajoStreamEvent event) {
//							simple
//						}
//					};
//				}
//				return out;
//
//			}
//		};
//	}

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
	private static String determineTenantFromRequest(final HttpServletRequest req) {
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

	private static Subscriber<byte[]> createOutput(AsyncContext context) throws IOException {
		ServletOutputStream out = context.getResponse().getOutputStream();
		final AtomicBoolean done = new AtomicBoolean(false);
		final Queue<byte[]> outputQueue = new ConcurrentLinkedQueue<byte[]>();
		
		out.setWriteListener(new WriteListener() {
			
			@Override
			public void onWritePossible() throws IOException {
				System.err.println("Write possible!");
				if(done.get() && out.isReady()) {
					System.err.println("Done switch trapped and write complete, closing context.");
//					context.complete();
				}
				while(!outputQueue.isEmpty()) {
					byte[] element = outputQueue.poll();
					if(element!=null) {
						System.err.println("Backlog queue, writing");
						out.write(element,0,element.length);
					}
					if(!out.isReady()) {
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
				e.printStackTrace();
				try {
					// TODO Does not work with Async. Duh.
					((HttpServletResponse)context.getResponse()).sendError(501, "Error in Navajo");
				} catch (IOException e1) {
					logger.error("Error: ", e1);
				}
			}
		});
		
		return new Subscriber<byte[]>(){
			
			private long bufferedTotal = 0;
			@Override
			public void onStart() {
				request(1);
			}
			
			@Override
			public void onCompleted() {
				try {
//					out.flush();
					// TODO Race condition here, races with last write complete
//					context.complete();
//					out.close();
//					System.err.println("Input completed. Setting done: "+done.get());
//					done.compareAndSet(false,true);
//					System.err.println("Done: "+done.get());
					context.complete();
					out.close();
					
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
					
					if(!out.isReady()) {
						bufferedTotal+=b.length;
						System.err.println("Not ready... Current size: "+outputQueue.size()+" total: "+bufferedTotal);
						outputQueue.offer(b);
					} else {
//						System.err.println("Ready, checking queue first. Empty? "+outputQueue.isEmpty()+" length: "+b.length);
						while(!outputQueue.isEmpty()) {
							byte[] element = outputQueue.poll();
							out.write(element);
							request(1);
							if(!out.isReady()) {
								System.err.println("not ready any more,breaking");
								break;
							}
						}

						if(out.isReady()) {
							out.write(b);
							request(1);
						}						
					}
				} catch (Exception e) {
					logger.error("Error: ", e);
					onError(e);
					unsubscribe();
				}
				
			}
		};
	}

}
