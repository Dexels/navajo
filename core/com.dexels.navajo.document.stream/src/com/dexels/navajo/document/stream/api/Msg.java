package com.dexels.navajo.document.stream.api;

import java.io.StringWriter;
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
	private final String mode;
	private final MessageType type;
	private final List<Prop> properties = new ArrayList<>();
	private final Map<String,Prop> propertiesByName = new HashMap<>();
	private final Func1<Msg,Observable<NavajoStreamEvent>> subMessages;
	private final Action1<Msg> msgAction;
	private enum MessageType {
		SIMPLE,DEFINITION,ARRAY_ELEMENT
	};
	
	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		sw.write("Name: "+name+" mode: "+mode+" type: "+type+"\n");
		for (Prop prop : properties) {
			sw.write("  "+prop+"\n");
		}
		sw.write("-----\n");
		return sw.toString();
	}
	
	public Msg copy() {
		return new Msg(this);
	}

	private Msg(List<Prop> properties,Msg.MessageType type) {
		this.name = "Unnamed";
		this.type = type;
		this.mode = null;
		this.subMessages = f->Observable.empty();
		this.msgAction = m->{};
		this.properties.addAll(properties);
		properties.stream().forEach(a->propertiesByName.put(a.name(), a));
	}
	
	private Msg(Msg source) {
		this.name = source.name;
		this.type = source.type;
		this.mode = source.mode;
		this.subMessages = f->Observable.empty();
		this.msgAction = m->{};		
		this.properties.addAll(source.properties);
		this.propertiesByName.putAll(source.propertiesByName);
	}

	public static Msg createElement() {
		return new Msg(Collections.emptyList(),Msg.MessageType.ARRAY_ELEMENT);
	}
	
	public static Msg create() {
		return new Msg(Collections.emptyList(),Msg.MessageType.SIMPLE);
	}
	
	public static Msg createElement(List<Prop> properties) {
		return new Msg(properties,Msg.MessageType.ARRAY_ELEMENT);
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

	public Msg renameProperty(String from, String to) {
		Msg mm = new Msg(this);
		Prop prev = propertiesByName.get(from);
		Prop renamed = prev.withName(to);
		int index = properties.indexOf(prev);
		mm.properties.set(index, renamed);
		mm.propertiesByName.remove(from);
		mm.propertiesByName.put(to, renamed);
		return mm;
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
			return Observable.<NavajoStreamEvent>just(Events.arrayElementStarted(Collections.emptyMap()));
		case SIMPLE:
			return Observable.<NavajoStreamEvent>just(Events.messageStarted(name,Collections.emptyMap()));
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
			return Observable.<NavajoStreamEvent>just(Events.arrayElement(properties,Collections.emptyMap()));
		case SIMPLE:
			return Observable.<NavajoStreamEvent>just(Events.message(properties,name,Collections.emptyMap()));
		case DEFINITION:
			return Observable.<NavajoStreamEvent>just(Events.messageDefinition(properties,name));
		default:
			break;
		}
		return Observable.error(new IllegalArgumentException("Unexpected type:"+ type));

	}
}
