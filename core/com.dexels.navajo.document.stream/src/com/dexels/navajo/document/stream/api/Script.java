package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.FlowableTransformer;

public interface Script {
	public FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent> call(StreamScriptContext context);

}
