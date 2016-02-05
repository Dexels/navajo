package com.dexels.navajo.document.stream.api;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class Msg {
	private final String name;
	private final List<Prop> properties = new ArrayList<>();
	private final MessageType type;
	private final Func1<Msg,Observable<NavajoStreamEvent>> subMessages;
	private final Action1<Msg> msgAction;
	private enum MessageType {
		SIMPLE,DEFINITION,ARRAY_ELEMENT
	};
	
	private Msg(String name, MessageType type,Action1<Msg> msgAction, Func1<Msg,Observable<NavajoStreamEvent>> body) {
		this.name = name;
		this.type = type;
		this.subMessages = body;
		this.msgAction = msgAction;
	}

	public static Observable<NavajoStreamEvent> create(String name, Action1<Msg> msgAction, Func1<Msg,Observable<NavajoStreamEvent>> body) {
		return new Msg(name,MessageType.SIMPLE,msgAction,body).stream();
	}

	public static Observable<NavajoStreamEvent> createElement(String name, Action1<Msg> msgAction, Func1<Msg,Observable<NavajoStreamEvent>> body) {
		return new Msg(name,MessageType.ARRAY_ELEMENT,msgAction,body).stream();
	}

	public Prop add(Prop property) {
		properties.add(property);
		return property;
	}
	
	public Observable<NavajoStreamEvent> stream() {
		msgAction.call(this);
		return subMessages.call(this).startWith(before()).concatWith(after());
	}

	private Observable<NavajoStreamEvent> before() {
		switch (type) {
		case ARRAY_ELEMENT:
			return Observable.<NavajoStreamEvent>just(Events.arrayElementStarted(name));
		case SIMPLE:
			return Observable.<NavajoStreamEvent>just(Events.messageStarted(name));
		case DEFINITION:
			return Observable.<NavajoStreamEvent>just(Events.messageDefinitionStarted(name));
		default:
			break;
		}
		return Observable.error(new IllegalArgumentException("Unexpected type:"+ type));
	}
	
	private Observable<NavajoStreamEvent> after() {
		switch (type) {
		case ARRAY_ELEMENT:
			return Observable.<NavajoStreamEvent>just(Events.arrayElement(properties,name));
		case SIMPLE:
			return Observable.<NavajoStreamEvent>just(Events.message(properties,name));
		case DEFINITION:
			return Observable.<NavajoStreamEvent>just(Events.messageDefinition(properties,name));
		default:
			break;
		}
		return Observable.error(new IllegalArgumentException("Unexpected type:"+ type));

	}
}
