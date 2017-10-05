package com.dexels.navajo.reactive.api;

import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public interface ReactiveScript {
	public Flowable<NavajoStreamEvent> execute(StreamScriptContext context);
}
