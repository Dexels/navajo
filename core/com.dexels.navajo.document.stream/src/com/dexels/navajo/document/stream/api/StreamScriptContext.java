package com.dexels.navajo.document.stream.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.AsyncContext;

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
	private final Optional<Maybe<Navajo>> inputNavajo;
	public final Map<String, Object> attributes;
	private final Optional<ReactiveScriptRunner> runner;
	private String deployment;
	private final List<String> methods;
	private final String uuid;
	private Navajo resolvedNavajo = null;
	private final Optional<Disposable> disposable;
//	private final Optional<AsyncContext> asyncContext;
	
	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(tenant,service,Optional.empty(),Optional.empty(),Collections.emptyMap(),Optional.empty(),Optional.of( Maybe.empty()),Optional.empty(),Collections.emptyList(),Optional.empty());
		this.deployment = deployment;
	}
	
	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Optional<Maybe<Navajo>> inputNavajo, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Disposable> disposable) {
		this.tenant = tenant;
		this.service = service;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
		this.inputFlowable = input;
		this.inputNavajo = inputNavajo;
		this.runner = runner;
		this.deployment = runner.map(r->r.deployment()).orElse("");
		this.methods = addedMethods;
		this.uuid = UUID.randomUUID().toString();
		this.disposable = disposable;
//		this.asyncContext = asyncContext;
	}


	public String uuid() {
		return uuid;
	}
	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}

	public StreamScriptContext withOnComplete(Runnable onComplete) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}

	public StreamScriptContext withUsername(Optional<String> username) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}
	
	public StreamScriptContext withPassword(Optional<String> password) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods,disposable);
	}
	
	public StreamScriptContext withInput(Flowable<NavajoStreamEvent> input) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, Optional.of(input),Optional.empty(), runner, methods,disposable);
	}

	public StreamScriptContext withInputNavajo(Navajo input) {
		if(inputFlowable.isPresent()) {
			throw new IllegalArgumentException("Can not attach resolved input  when there is a input flowable");
		}
		return new StreamScriptContext(this.tenant, service, username, password, attributes, Optional.empty(),Optional.of(Maybe.just(input)), runner, methods,disposable);
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

	public Navajo getBlockingInput() {
		if(this.resolvedNavajo == null) {
			this.resolvedNavajo = getInput().blockingGet();
		}
		return this.resolvedNavajo;
	}
	
	public Single<Navajo> getInput() {
		return collect().toSingle(NavajoFactory.getInstance().createNavajo())
				.map(e->{e.addHeader(NavajoFactory.getInstance().createHeader(e, service, username.orElse(""), password.orElse(""), -1));
					return e;
				})
				;
	}
	
	private Maybe<Navajo> collect() {
		if(!inputFlowable.isPresent()) {
			return inputNavajo.orElse(Maybe.empty());
		}
		return inputFlowable.get()
				.toObservable().compose(StreamDocument.domStreamCollector()).firstElement();
	}
	
	public void cancel() {
		if(this.disposable.isPresent()) {
			this.disposable.get().dispose();
		}
	}
}
