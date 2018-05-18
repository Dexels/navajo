package com.dexels.navajo.document.stream.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class StreamScriptContext {

	public final String tenant;
	public final String service;
	public final Optional<String> username;
	public final Optional<String> password;
	private final Optional<Flowable<NavajoStreamEvent>> inputFlowable;
	private final Navajo inputNavajo;
	public final Map<String, Object> attributes;

	private final Optional<RunningReactiveScripts> runningScripts;
	private final Optional<ReactiveScriptRunner> runner;
	private String deployment;
	private final List<String> methods;
	private final String uuid;
	private final Optional<Runnable> onDispose;
	public final Navajo authNavajo;
	public final long started;
	
	
	private final static Logger logger = LoggerFactory.getLogger(StreamScriptContext.class);

	//	private final Optional<AsyncContext> asyncContext;
	
	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(),tenant,service,Optional.empty(),Optional.empty(),null,Collections.emptyMap(),Optional.empty(),null,Optional.empty(),Collections.emptyList(),Optional.empty(),Optional.empty());
		this.deployment = deployment;
	}

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password,Navajo authNavajo, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Navajo inputNavajo, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose, Optional<RunningReactiveScripts> running) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(), tenant,service,username,password,authNavajo,attributes,input,inputNavajo,runner,addedMethods,onDispose,running);
	}
	
	private StreamScriptContext(String uuid, long started, String tenant, String service, Optional<String> username, Optional<String> password,Navajo authNavajo, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Navajo inputNavajo, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose, Optional<RunningReactiveScripts> running) {
		this.uuid = uuid;
		this.tenant = tenant;
		this.started = started;
		this.service = service;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
		this.inputFlowable = input;
		this.inputNavajo = inputNavajo;
		this.runner = runner;
		this.deployment = runner.map(r->r.deployment()).orElse("");
		this.methods = addedMethods;
		this.onDispose = onDispose;
		this.authNavajo = authNavajo;
		this.runningScripts = running;
//		this.asyncContext = asyncContext;
	}
	
	public String uuid() {
		return uuid;
	}
	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.uuid,this.started, this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext copyWithNewUUID() {
		String uuid = UUID.randomUUID().toString();
		StreamScriptContext streamScriptContext = new StreamScriptContext(uuid,this.started, this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,Optional.empty(),this.runningScripts);
//		this.runningScripts.get().submit(streamScriptContext);
		return streamScriptContext;
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext withOnComplete(Runnable onComplete) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext withUsername(Optional<String> username) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,onDispose,this.runningScripts);
	}
	
	public StreamScriptContext withPassword(Optional<String> password) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,onDispose,this.runningScripts);
	}
	
	public StreamScriptContext withInput(Flowable<NavajoStreamEvent> input) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, Optional.of(input),null, runner, methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext withInputNavajo(Navajo input) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, Optional.empty(),input, runner, methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext withDispose(Runnable disposer) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner, methods,Optional.of(disposer),this.runningScripts);
	}

	public ReactiveScriptRunner runner() {
		return runner.orElseThrow(()->new RuntimeException("StreamScriptContext has no runner, so sub runners aren't allowed"));
	}

	public Optional<RunningReactiveScripts> runningScripts() {
		return this.runningScripts;
	}

	public String deployment() {
		return deployment;
	}
	
	public Flowable<NavajoStreamEvent> inputFlowable() {
		return inputFlowable.orElse(Flowable.empty());
	}
	
	public Single<Navajo> getInput() {
		return collect().toSingle(NavajoFactory.getInstance().createNavajo())
				.map(e->{e.addHeader(NavajoFactory.getInstance().createHeader(e, service, username.orElse(""), password.orElse(""), -1));
					return e;
				});
	}
	
	private Maybe<Navajo> collect() {
		if(inputNavajo!=null) {
			return Maybe.error(()->new IllegalStateException("Can't collect navajo when input is already present"));
		}
		return inputFlowable.get()
				.toObservable().compose(StreamDocument.domStreamCollector()).firstElement();
	}
	
	public void cancel() {
		if(this.onDispose.isPresent()) {
			this.onDispose.get().run();
		}
	}
	
	public Navajo resolvedNavajo() {
		return inputNavajo;
	}

	public void logEvent(String message) {
		logger.info("Stream Context event service: {} uuid: {} message: {}",service,uuid,message);
	}

	public void complete() {
		if(this.runningScripts.isPresent()) {
			this.runningScripts.get().completed(this);
		}
	}
}
