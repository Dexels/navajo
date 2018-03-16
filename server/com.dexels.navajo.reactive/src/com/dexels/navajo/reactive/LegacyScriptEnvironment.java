package com.dexels.navajo.reactive;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
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
import io.reactivex.Observable;

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
				return runLegacy(context.inputFlowable().compose(StreamDocument.inNavajo(service, context.username, context.password)), context,debug).map(DataItem::of);			
				
			}
			
			@Override
			public Type dataType() {
				return Type.EVENT;
			}

			@Override
			public Optional<String> binaryMimeType() {
				return Optional.empty();
			}

			@Override
			public Optional<String> streamMessage() {
				return Optional.empty();
			}

			@Override
			public List<ReactiveParseProblem> problems() {
				return Collections.emptyList();
			}
		};
//		
	}

	@Override
	public boolean acceptsScript(String service) {
		return true;
	}


	private Flowable<NavajoStreamEvent> runLegacy(Flowable<NavajoStreamEvent> eventStream,
			StreamScriptContext context, boolean debug) {
		return eventStream
				.lift(StreamDocument.collectFlowable())
				.doOnNext(nav->{
					if(debug) {
						logger.warn("======== DEBUG REQUEST============");
						nav.write(System.err);
						logger.warn("=== END OF DEBUG REQUEST============");
					}
				})
				.flatMap(inputNav->executeLegacy(context, inputNav));
	}
	

	private final Flowable<NavajoStreamEvent> executeLegacy(StreamScriptContext context,Navajo in) {
		Navajo result;
		try {
			result = execute(context, in);
		} catch (Throwable e) {
			logger.error("Error: ", e);
			return errorMessage(context.service,context.username,101,"Could not resolve script: "+context.service);
		}
		return Observable.just(result)
			.lift(StreamDocument.domStream())
			.toFlowable(BackpressureStrategy.BUFFER);
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
				in.write(System.err);
				in.getHeader().setRPCUser(context.username.get());
				in.getHeader().setRPCPassword(context.password.get());
				Navajo outDoc = getLocalClient().handleInternal(context.tenant, in, null, null);
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

	private static Flowable<NavajoStreamEvent> errorMessage(String service, Optional<String> user, int code, String message) {
		return Msg.create("error")
				.with(Prop.create("code",code))
				.with(Prop.create("description", message))
				.stream()
				.toFlowable(BackpressureStrategy.BUFFER)
				.compose(StreamDocument.inNavajo(service, user, Optional.empty()));
	}

	@Override
	public String deployment() {
		return null;
	}
}
