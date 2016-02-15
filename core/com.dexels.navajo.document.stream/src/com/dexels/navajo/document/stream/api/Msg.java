package com.dexels.navajo.document.stream.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class Msg {
	private final String name;
	private final List<Prop> properties = new ArrayList<>();
	private final Map<String,Prop> propertiesByName = new HashMap<>();
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

	public Msg(List<Prop> properties) {
		this.name = "Unnamed";
		this.type = Msg.MessageType.ARRAY_ELEMENT;
		this.subMessages = f->Observable.empty();
		this.msgAction = m->{};
		this.properties.addAll(properties);
		properties.stream().forEach(a->propertiesByName.put(a.name(), a));
	}

	@Deprecated
	public static Observable<NavajoStreamEvent> create(String name, Action1<Msg> msgAction, Func1<Msg,Observable<NavajoStreamEvent>> body) {
		return new Msg(name,MessageType.SIMPLE,msgAction,body).stream();
	}
	public static Msg createElement() {
		return new Msg(Collections.emptyList());
	}
	public static Msg createElement(List<Prop> properties) {
		return new Msg(properties);
	}


	@Deprecated
	public static Observable<NavajoStreamEvent> createElement(String name, Action1<Msg> msgAction, Func1<Msg,Observable<NavajoStreamEvent>> body) {
		return new Msg(name,MessageType.ARRAY_ELEMENT,msgAction,body).stream();
	}

	@Deprecated
	public static Observable<NavajoStreamEvent> createElement(String name, Func1<Msg,Observable<NavajoStreamEvent>> body) {
		return new Msg(name,MessageType.ARRAY_ELEMENT,m->{},body).stream();
	}

	@Deprecated
	public static Observable<NavajoStreamEvent> createElement(String name, Action1<Msg> msgAction) {
		return new Msg(name,MessageType.ARRAY_ELEMENT,msgAction,f->Observable.empty()).stream();
	}

	
	public Prop add(Prop property) {
		with(property);
		return property;
	}

	public Msg with(Prop property) {
		properties.add(property);
		propertiesByName.put(property.name(), property);
		return this;
	}

	public Msg without(String name) {
		Prop toBeRemoved = propertiesByName.get(name);
		if(toBeRemoved!=null) {
			properties.remove(toBeRemoved);
			propertiesByName.remove(name);
		}
		return this;
	}
	
	public Msg without(String[] names) {
		for (String name : names) {
			without(name);
		}
		return this;
	}
	
	
	public Object value(String name) {
		Prop p = propertiesByName.get(name);
		if(p==null) {
			return null;
		}
		return p.value();
	}

	public Observable<NavajoStreamEvent> stream() {
		msgAction.call(this);
		return subMessages.call(this).startWith(before()).concatWith(after());
	}

	private Observable<NavajoStreamEvent> before() {
		switch (type) {
		case ARRAY_ELEMENT:
			return Observable.<NavajoStreamEvent>just(Events.arrayElementStarted());
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
			return Observable.<NavajoStreamEvent>just(Events.arrayElement(properties));
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
