package com.dexels.navajo.document.stream.api;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class Msg {
	private final String name;
	private final String mode;
	private final MessageType type;
	private final List<Prop> properties = new ArrayList<>();
	private final Map<String,Prop> propertiesByName = new HashMap<>();
	
	
	private final static Logger logger = LoggerFactory.getLogger(Msg.class);

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
	
	public ImmutableMessage toImmutableMessage() {
		Map<String,Object> values = new HashMap<>();
		Map<String,String> types = new HashMap<>();
		for (Prop prop : properties) {
			String type = prop.type();
			types.put(prop.name(), type);
			switch (type) {
			case "string":
				values.put(prop.name(), prop.valueAsString());
				break;
			case "integer":
				values.put(prop.name(), Integer.parseInt( prop.valueAsString()));
				break;
			case "long":
				values.put(prop.name(), Long.parseLong(prop.valueAsString()));
				break;
			case "double":
				values.put(prop.name(), Double.parseDouble(prop.valueAsString()));
				break;
			case "float":
				values.put(prop.name(), Float.parseFloat(prop.valueAsString()));
				break;
			case "boolean":
				values.put(prop.name(), Boolean.parseBoolean(prop.valueAsString()));
				break;
			case "binary_digest":
				values.put(prop.name(), prop.valueAsString());
				break;

			case "date":
				//"2011-10-03 15:01:06.00"
				try {
					values.put(prop.name(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").parse( prop.valueAsString()));
				} catch (ParseException e) {
				    logger.warn("Cannot parse date {} = returning null", prop.value());
					return null;
				}
			case "clocktime":
                //"15:01:06"
                try {
                    values.put(prop.name(),new SimpleDateFormat("HH:mm:ss").parse( prop.valueAsString()));

                } catch (ParseException e) {
                    logger.warn("Cannot parse clocktime {} = returning null", prop.value());

                    return null;
                }
			default:
			    logger.warn("Unsupported type {}", type);
				break;
		}
			System.err.println("Name: "+prop.name()+" -> "+prop.type()+" obje: "+prop.value().getClass());
		}
		// TODO
		return ImmutableFactory.create(values, types);
	}
	
	public Msg copy() {
		return new Msg(this);
	}

	private Msg(Optional<String> name, List<Prop> properties,Msg.MessageType type) {
		this.name = name.orElse("Unnamed");
		this.type = type;
		this.mode = null;
		this.properties.addAll(properties);
		properties.stream().forEach(a->propertiesByName.put(a.name(), a));
	}
	
	private Msg(Msg source) {
		this.name = source.name;
		this.type = source.type;
		this.mode = source.mode;
		this.properties.addAll(source.properties);
		this.propertiesByName.putAll(source.propertiesByName);
	}

	public static Msg createElement() {
		return new Msg(Optional.empty(), Collections.emptyList(),Msg.MessageType.ARRAY_ELEMENT);
	}
	
	public static Msg create() {
		return new Msg(Optional.empty(),Collections.emptyList(),Msg.MessageType.SIMPLE);
	}
	
	public static Msg create(String name) {
		return new Msg(Optional.of(name),Collections.emptyList(),Msg.MessageType.SIMPLE);
	}
	
	public static Msg createDefinition() {
		return new Msg(Optional.empty(),Collections.emptyList(),Msg.MessageType.DEFINITION);
	}
	public static Msg create(List<Prop> properties) {
		return new Msg(Optional.empty(),properties,Msg.MessageType.SIMPLE);
	}
	
	public static Msg createDefinition(List<Prop> properties) {
		return new Msg(Optional.empty(),properties,Msg.MessageType.DEFINITION);
	}
	
	public static Msg createElement(List<Prop> properties) {
		return new Msg(Optional.empty(),properties,Msg.MessageType.ARRAY_ELEMENT);
	}

	public Prop add(Prop property) {
		with(property);
		return property;
	}

	public Msg withValue(String propertyName, String value) {
		return copy().with(property(propertyName).withValue(value));
	}
	public Msg withName(String messageName) {
		return new Msg(Optional.of(messageName),properties,type);
	}
	
	
	private Prop property(String name) {
		return propertiesByName.get(name);
	}
	public List<Prop> properties() {
		return Collections.unmodifiableList(properties);
	}
	public Msg with(Prop property) {
		Prop old = propertiesByName.get(property.name());
		if(old!=null) {
			properties.remove(old);
		}
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
	public String stringValue(String name) {
		Prop p = propertiesByName.get(name);
		if(p==null) {
			return null;
		}
		Object value = p.value();
		if(value==null) {
			return null;
		}
		if(value instanceof String) {
			return (String)value;
		}
		return value.toString();
	}
	public Observable<NavajoStreamEvent> stream() {
		return Observable.just(before(), after());
	}
	public Flowable<NavajoStreamEvent> streamFlowable() {
		return Flowable.just(before(), after());
	}

	
	private NavajoStreamEvent before()  {
		switch (type) {
		case ARRAY_ELEMENT:
			return Events.arrayElementStarted(Collections.emptyMap());
		case SIMPLE:
			return Events.messageStarted(name,Collections.emptyMap());
		case DEFINITION:
			return Events.messageDefinitionStarted(name);
		default:
			break;
		}
		throw new IllegalArgumentException("Unexpected type:"+ type);
	}
	
	private NavajoStreamEvent after() {
		switch (type) {
		case ARRAY_ELEMENT:
			return Events.arrayElement(this,Collections.emptyMap());
		case SIMPLE:
			return Events.message(this,name,Collections.emptyMap());
		case DEFINITION:
			return Events.messageDefinition(this,name);
		default:
			break;
		}
		throw new IllegalArgumentException("Unexpected type:"+ type);
	}
}
