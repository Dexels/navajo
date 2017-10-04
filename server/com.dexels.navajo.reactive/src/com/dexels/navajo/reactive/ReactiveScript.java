package com.dexels.navajo.reactive;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public interface ReactiveScript {
	public Flowable<NavajoStreamEvent> execute(StreamScriptContext context,Navajo in, Message current);
}
