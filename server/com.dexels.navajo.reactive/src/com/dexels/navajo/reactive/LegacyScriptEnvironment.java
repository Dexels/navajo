package com.dexels.navajo.reactive;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
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
			public Flowable<DataItem> execute(StreamScriptContext context) {
				try {
					Flowable<DataItem> map = Flowable.just(DataItem.ofEventStream(runLegacy(context,debug)));
					return map;
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
		};
//		
	}

	@Override
	public boolean acceptsScript(String service) {
		return true;
	}


	private Flowable<NavajoStreamEvent> runLegacy(StreamScriptContext context, boolean debug) {
		Single<Navajo> in = context.getInput();
		return in
			.doOnSuccess(nav->
				{
					if(debug) {
						logger.warn("======== DEBUG REQUEST============");
						nav.write(System.err);
						logger.warn("=== END OF DEBUG REQUEST============");
					}
				})
			.map(inputNav->executeLegacy(context,inputNav))
			.toFlowable()
			.concatMap(e->e)
		;
	}
	 

	private final Flowable<NavajoStreamEvent> executeLegacy(StreamScriptContext context, Navajo input) {
		try {
				context.authNavajo.getAllMessages().forEach(message->{
					input.addMessage(message);
				});
				Navajo result = execute(context, input);
				return Single.just(result)
						.compose(StreamDocument.domStreamTransformer())
						.toObservable()
						.flatMap(e->e)
//						.filter(e->e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_DONE && e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_STARTED)
						.toFlowable(BackpressureStrategy.BUFFER);
			} catch (Throwable e) {
				logger.error("Error: ", e);
 				return errorMessage(context.service,context.username,101,"Could not resolve script: "+context.service);
			}
			
	}
	
	private final Navajo execute(StreamScriptContext context, Navajo in) throws IOException {

		if (context.tenant != null) {
			MDC.put("instance", context.tenant);
		}
		try {
			in.getHeader().setHeaderAttribute("useComet", "true");
			if (in.getHeader().getHeaderAttribute("callback") != null) {

				String callback = in.getHeader().getHeaderAttribute("callback");

				Navajo callbackNavajo = getLocalClient().handleCallback(context.tenant, in, callback);
				return callbackNavajo;

			} else {
				in.getHeader().setRPCUser(context.username.get());
				in.getHeader().setRPCPassword(context.password.get());
				Navajo outDoc = getLocalClient().handleInternal(context.tenant,in,true);

//				Navajo outDoc = getLocalClient().handleInternal(context.tenant, in, null, createClientInfo(context));
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
	private static Flowable<NavajoStreamEvent> errorMessage(String service, Optional<String> user, int code, String message) {
		return Msg.create("error")
				.with(Prop.create("code",""+code,Property.INTEGER_PROPERTY))
				.with(Prop.create("description", message))
				.stream()
				.toFlowable(BackpressureStrategy.BUFFER)
				.compose(StreamDocument.inNavajo(service, user, Optional.empty()));
	}

	@Override
	public String deployment() {
		// TODO
		return null;
	}
}
