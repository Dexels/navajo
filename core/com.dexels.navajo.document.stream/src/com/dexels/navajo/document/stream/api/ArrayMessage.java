package com.dexels.navajo.document.stream.api;

import java.util.Collections;

import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.functions.Func1;

public class ArrayMessage {
	private final String name;
	private final Func1<ArrayMessage,Observable<NavajoStreamEvent>> body;

	public ArrayMessage(String name, Func1<ArrayMessage,Observable<NavajoStreamEvent>> body) {
		this.name = name;
		this.body = body;
	}

	public static ArrayMessage createArray(String name,Func1<ArrayMessage,Observable<NavajoStreamEvent>> body) {
		return new ArrayMessage(name,body);
	}
	
	public Observable<NavajoStreamEvent> stream() {
		return body.call(this).startWith(before()).concatWith(after());
	}
	
	private Observable<NavajoStreamEvent> before() {
		return Observable.<NavajoStreamEvent>just(Events.arrayStarted(name,Collections.emptyMap()));
	}
	
	private Observable<NavajoStreamEvent> after() {
		return Observable.<NavajoStreamEvent>just(Events.arrayDone(name));
	}
}
