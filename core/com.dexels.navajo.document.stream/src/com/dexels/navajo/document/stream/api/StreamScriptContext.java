package com.dexels.navajo.document.stream.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public class StreamScriptContext {

	public final String tenant;
	public final String service;
	public final Optional<String> username;
	public final Optional<String> password;
	private final Optional<Flowable<NavajoStreamEvent>> inputFlowable;
	private final Optional<Navajo> inputNavajo;
	public final Map<String, Object> attributes;
	private final Optional<ReactiveScriptRunner> runner;
	private String deployment;
	private final List<String> methods;

	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(tenant,service,Optional.empty(),Optional.empty(),Collections.emptyMap(),Optional.empty(),Optional.empty(),Optional.empty(),Collections.emptyList());
		this.deployment = deployment;
	}
	
	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Optional<Navajo> inputNavajo, Optional<ReactiveScriptRunner> runner, List<String> addedMethods) {
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
	}

	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods);
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable,inputNavajo, runner,methods);
	}

	public StreamScriptContext withInput(Flowable<NavajoStreamEvent> input) {
		if(inputNavajo.isPresent()) {
			throw new IllegalArgumentException("Can not attach input flowable when there is a resolved input already");
		}
		return new StreamScriptContext(this.tenant, service, username, password, attributes, Optional.of(input),Optional.empty(), runner, methods);
	}

	public StreamScriptContext withInputNavajo(Navajo input) {
		if(inputFlowable.isPresent()) {
			throw new IllegalArgumentException("Can not attach resolved input  when there is a input flowable");
		}
		return new StreamScriptContext(this.tenant, service, username, password, attributes, Optional.empty(),Optional.of(input), runner, methods);
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

	public Optional<Navajo> getInput() {
		return this.inputNavajo;
	}
	
	public StreamScriptContext resolveInput() {
		if(!inputFlowable.isPresent()) {
			throw new IllegalArgumentException("Can not resolved input: No unresolved input present. Is it resolved already?");
		}
		if(inputNavajo.isPresent()) {
			throw new IllegalArgumentException("Resolved input already present. Is it resolved already?");
		}
		Navajo inputNavajo = inputFlowable.get().toObservable().lift(StreamDocument.collectNew()).concatMap(e->e).first(NavajoFactory.getInstance().createNavajo()).blockingGet();

		return new StreamScriptContext(this.tenant, service, username, password, attributes, Optional.empty(),Optional.of(inputNavajo), runner, methods);
	}

}
