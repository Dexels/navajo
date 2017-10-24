package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public interface ReactiveScriptRunner {
	public Flowable<NavajoStreamEvent> run(StreamScriptContext context, String service, Flowable<NavajoStreamEvent> input);
//	public StreamScriptContext createContext(StreamScriptContext context, String service, Flowable<NavajoStreamEvent> input);

}
