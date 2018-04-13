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
import io.reactivex.disposables.Disposable;

public class StreamScriptContext {

	public final String tenant;
	public final String service;
	public final Optional<String> username;
	public final Optional<String> password;
	private final Optional<Flowable<NavajoStreamEvent>> inputFlowable;
	private final Navajo inputNavajo;
	public final Map<String, Object> attributes;
	private final Optional<ReactiveScriptRunner> runner;
	private String deployment;
	private final List<String> methods;
	private final String uuid;
	private final Optional<Disposable> disposable;
	public final Navajo authNavajo;
	public final long started;
	
	
	private final static Logger logger = LoggerFactory.getLogger(StreamScriptContext.class);

	//	private final Optional<AsyncContext> asyncContext;
	
	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(),tenant,service,Optional.empty(),Optional.empty(),null,Collections.emptyMap(),Optional.empty(),null,Optional.empty(),Collections.emptyList(),Optional.empty());
		this.deployment = deployment;
	}

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password,Navajo authNavajo, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Navajo inputNavajo, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Disposable> disposable) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(), tenant,service,username,password,authNavajo,attributes,input,inputNavajo,runner,addedMethods,disposable);
	}
	
	public StreamScriptContext(String uuid, long started, String tenant, String service, Optional<String> username, Optional<String> password,Navajo authNavajo, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Navajo inputNavajo, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Disposable> disposable) {
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
		this.disposable = disposable;
		this.authNavajo = authNavajo;
//		this.asyncContext = asyncContext;
	}
	
	public String uuid() {
		return uuid;
	}
	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.uuid,this.started, this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}

	public StreamScriptContext withOnComplete(Runnable onComplete) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}

	public StreamScriptContext withUsername(Optional<String> username) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}
	
	public StreamScriptContext withPassword(Optional<String> password) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}
	
	public StreamScriptContext withInput(Flowable<NavajoStreamEvent> input) {
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, Optional.of(input),null, runner, methods,disposable);
	}

	public StreamScriptContext withInputNavajo(Navajo input) {
//		if(inputFlowable.isPresent()) {
//			throw new IllegalArgumentException("Can not attach resolved input  when there is a input flowable");
//		}
		return new StreamScriptContext(this.uuid,this.started,this.tenant, service, username, password, authNavajo, attributes, Optional.empty(),input, runner, methods,disposable);
	}

	public ReactiveScriptRunner runner() {
		return runner.orElseThrow(()->new RuntimeException("StreamScriptContext has no runner, so sub runners aren't allowed"));
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
		if(this.disposable.isPresent()) {
			this.disposable.get().dispose();
		}
	}
	
	public Navajo resolvedNavajo() {
		return inputNavajo;
	}

	public void logEvent(String message) {
		logger.info("Stream Context event service: {} uuid: {} message: {}",service,uuid,message);
	}
}
