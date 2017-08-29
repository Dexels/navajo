package com.dexels.navajo.document.stream.api;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public interface SimpleScript  extends FlowableTransformer<NavajoStreamEvent,Navajo> {
	public Flowable<NavajoStreamEvent> apply(Navajo input, StreamScriptContext context);
}
