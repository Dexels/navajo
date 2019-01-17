package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.CompiledReactiveScript;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LegacyScriptEnvironment implements ReactiveScriptRunner {

	
	private final static Logger logger = LoggerFactory.getLogger(LegacyScriptEnvironment.class);
	
	public LegacyScriptEnvironment() {
		// TODO Auto-generated constructor stub
	}
	
	
	private LocalClient localClient;
	
	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}

	@Override
	public ReactiveScript build(String service, boolean debug) {
		return new ReactiveScript() {
			
			@Override
			public Flowable<Flowable<DataItem>> execute(StreamScriptContext context) {
				StreamScriptContext ctx = context.withService(service);
				try {
					return Flowable.just(Flowable.just(DataItem.ofEventStream(runLegacy(ctx,debug))));
				} catch (Exception e) {
					logger.error("Error: ", e);
					return Flowable.error(e);
				}			
				
			}
			
			@Override
			public Type dataType() {
				return Type.EVENTSTREAM;
			}

			@Override
			public Optional<String> binaryMimeType() {
				return Optional.empty();
			}

			@Override
			public List<ReactiveParseProblem> problems() {
				return Collections.emptyList();
			}

			@Override
			public boolean streamInput() {
				return false;
			}

			@Override
			public List<String> methods() {
				return Collections.emptyList();
			}
		};
//		
	}

	@Override
	public boolean acceptsScript(String service) {
		return true;
	}


	private Flowable<NavajoStreamEvent> runLegacy(StreamScriptContext context, boolean debug) {
//		Maybe<Navajo> in = context.collect();
		return executeLegacy(context)
				.doOnComplete(()->{
					if(debug) {
						logger.warn("======== DEBUG REQUEST============");
						context.resolvedNavajo().write(System.err);
						logger.warn("=== END OF DEBUG REQUEST============");
					}
				});
	}
	 

	private final Flowable<NavajoStreamEvent> executeLegacy(StreamScriptContext context) {
		String service = context.getService();
		System.err.println("Service: "+service);
		try {
//				context.resolvedNavajo().getAllMessages().forEach(message->{
//					input.addMessage(message);
//				});
				Navajo result = execute(context);
				logIfError(result);
				return Single.just(result)
						.compose(StreamDocument.domStreamTransformer())
						.toObservable()
						.flatMap(e->e)
//						.filter(e->e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_DONE && e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_STARTED)
						.toFlowable(BackpressureStrategy.BUFFER);
			} catch (Throwable e) {
				logger.error("Error: ", e);
 				return errorMessage(context.getService(),101,"Could not resolve script: "+context.getService());
			}
			
	}
	
	private void logIfError(Navajo result) {
		if(result.getMessage("error")!=null) {
			logger.info("Error:");
			result.write(System.err);
		}
		
	}

	private final Navajo execute(StreamScriptContext context) throws IOException {
		MDC.put("instance", context.getTenant());
		Navajo in;
		if(context.inputFlowable().isPresent()) {
			in = context.blockingNavajo().blockingGet();
		} else {
			in = context.resolvedNavajo();
		}
		in.removeHeader();
		if(in.getHeader()==null) {
			in.addHeader(NavajoFactory.getInstance().createHeader(in, context.getService(), context.getUsername(), "", -1));
		}
		try {
			in.getHeader().setHeaderAttribute("useComet", "true");
			if (in.getHeader().getHeaderAttribute("callback") != null) {

				String callback = in.getHeader().getHeaderAttribute("callback");

				Navajo callbackNavajo = getLocalClient().handleCallback(context.getTenant(), in, callback);
				return callbackNavajo;

			} else {
				in.getHeader().setRPCUser(context.getUsername());
				Navajo outDoc = getLocalClient().handleInternal(context.getTenant(), in, true);
				return outDoc;
			}
		} catch (Throwable e) {
			if (e instanceof FatalException) {
				FatalException fe = (FatalException) e;
				if (fe.getMessage().equals("500.13")) {
					// Server too busy.
					throw new IOException("500.13");
				}
			}
			throw new IOException(e);
		} finally {
			MDC.remove("instance");
		}
	}

//	private ClientInfo createClientInfo(StreamScriptContext context) {
//		String authHeader =  (String) context.attributes.get("Authorization");
//		String contentEncoding = (String) context.attributes.get("Content-Encoding");
//		String ip = "1.1.1.1";
//		
//		ClientInfo clientInfo = new ClientInfo(
//					ip,
//					"unknown",
//					contentEncoding,
//					(int) (0),
//					(int) (0),
//					0,
//					"reactive",
//					false,
//					false,
//					-1,
//					new java.util.Date());
//		clientInfo.setAuthHeader(authHeader);
//			return clientInfo;
//	}
	private static Flowable<NavajoStreamEvent> errorMessage(String service, int code, String message) {
		return Msg.create("error")
				.with(Prop.create("code",""+code,Property.INTEGER_PROPERTY))
				.with(Prop.create("description", message))
				.stream()
				.toFlowable(BackpressureStrategy.BUFFER)
				.compose(StreamDocument.inNavajo(service, Optional.empty(), Optional.empty()));
	}

	@Override
	public Optional<String> deployment() {
		return Optional.empty();
	}

	@Override
	public Optional<InputStream> sourceForService(String service) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompiledReactiveScript compiledScript(String service) {
		// TODO Auto-generated method stub
		return null;
	}
}
