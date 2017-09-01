package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.FlowableTransformer;

public abstract class StreamScript implements FlowableTransformer<NavajoStreamEvent,NavajoStreamEvent>, Script {

	protected final StreamScriptContext context;
	public StreamScript(StreamScriptContext context) {
		this.context = context;
	}



}
