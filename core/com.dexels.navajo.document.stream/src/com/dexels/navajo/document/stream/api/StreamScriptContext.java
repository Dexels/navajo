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
	private Flowable<NavajoStreamEvent> inputFlowable;
	public final Map<String, Object> attributes;
	private final Optional<ReactiveScriptRunner> runner;

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password, Map<String,Object> attributes, Optional<ReactiveScriptRunner> runner) {
		this.tenant = tenant;
		this.service = service;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
		this.inputFlowable = null;
		this.runner = runner;
	}
	
	public ReactiveScriptRunner runner() {
		return runner.orElseThrow(()->new RuntimeException("StreamScriptContext has no runner, so sub runners aren't allowed"));
	}
	
	
	public StreamScriptContext setInputFlowable(Flowable<NavajoStreamEvent> inputFlowable) {
		this.inputFlowable = inputFlowable;
		return this;
	}

	public Flowable<NavajoStreamEvent> inputFlowable() {
		return inputFlowable;
	}

	public Optional<Navajo> getInput() {
		if(this.input != null) {
			return Optional.of(this.input);
		} else {
			this.input = inputFlowable.toObservable().lift(StreamDocument.collect()).firstElement().blockingGet();
			this.inputFlowable = null;
			return Optional.of(this.input);
		}
	}

//	public void setNavajo(Navajo input) {
//		this.input = input;
//	}
	
//	public void setInputFlowable(Flowable<NavajoStreamEvent> input) {
//		this.inputFlowable = input;
//	}
}
