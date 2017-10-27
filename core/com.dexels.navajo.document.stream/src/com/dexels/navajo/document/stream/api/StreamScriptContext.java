package com.dexels.navajo.document.stream.api;

import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public class StreamScriptContext {

	public final String tenant;
	public final String service;
	public final Optional<String> username;
	public final Optional<String> password;
	private Navajo input = null;
	private Optional<Flowable<NavajoStreamEvent>> inputFlowable;
	public final Map<String, Object> attributes;
	private final Optional<ReactiveScriptRunner> runner;

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Optional<ReactiveScriptRunner> runner) {
		this.tenant = tenant;
		this.service = service;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
		this.inputFlowable = input;
		this.runner = runner;
	}



	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, inputFlowable, runner);
	}
	
	public StreamScriptContext withInput(Flowable<NavajoStreamEvent> input) {
		return new StreamScriptContext(this.tenant, service, username, password, attributes, Optional.of(input), runner);
	}

	public ReactiveScriptRunner runner() {
		return runner.orElseThrow(()->new RuntimeException("StreamScriptContext has no runner, so sub runners aren't allowed"));
	}
	
	
	public Flowable<NavajoStreamEvent> inputFlowable() {
		return inputFlowable.orElse(Flowable.empty());
	}

	public Optional<Navajo> getInput() {
		if(this.input != null) {
			return Optional.of(this.input);
		} else {
			this.input = inputFlowable.get().toObservable().lift(StreamDocument.collect()).firstElement().blockingGet();
			this.inputFlowable = null;
			return Optional.of(this.input);
		}
	}
}
