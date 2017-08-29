package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.FlowableTransformer;

public interface SimpleScript {
	public FlowableTransformer<Navajo,NavajoStreamEvent> apply(StreamScriptContext context);
}
