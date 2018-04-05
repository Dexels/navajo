package com.dexels.navajo.document.stream.events;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;

public class NavajoStreamEvent {
	protected final NavajoEventTypes type;
	private final Map<String, Object> attributes;
	private final String path;
	private final Object body;
	
	
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamEvent.class);

	
	NavajoStreamEvent(String path, NavajoEventTypes type, Object body, Map<String,? extends Object> attributes) {
		this.type = type;
		this.path = path;
		this.body = body;
		this.attributes = attributes == null? Collections.emptyMap() : Collections.unmodifiableMap(attributes);
	}

	@SuppressWarnings("unchecked")
	public String toString() {
		if(type==NavajoEventTypes.NAVAJO_STARTED) {
			NavajoHead h = (NavajoHead)body;
			return "Type: "+type+" path: "+path+" attributes: {"+attributes+"} - RPCNAME: "+h.name()+" user: "+h.username();
		}
		if(type==NavajoEventTypes.MESSAGE) {
			Msg msgBody = (Msg)body;
			List<Prop> contents = msgBody.properties();
			StringBuilder sb = new StringBuilder("Message detected. Name: "+path+" with mode: "+this.attributes.get("mode")+"\n");
			for (Prop prop : contents) {
				sb.append("Prop: "+prop.name()+" = "+prop.value()+ " value type: "+ prop.type() +"with direction: "+ prop.direction() + "\n");
				if(prop.value()!=null) {
					sb.append("   -> value class: "+prop.value().getClass()+"\n");
				}
			}
			return sb.toString();
		}
		if(type==NavajoEventTypes.NAVAJO_DONE) {
			List<String> methods = (List<String>)body;
			return "Navajo Done. Methods: "+methods;
		}
		return "Type: "+type+" path: "+path+" attributes: {"+attributes+"} "+body;
	}
	
	public enum NavajoEventTypes {
		MESSAGE_STARTED,
		MESSAGE,
		ARRAY_STARTED,
		ARRAY_ELEMENT,
		ARRAY_DONE,
		NAVAJO_DONE,
		NAVAJO_STARTED,
		ARRAY_ELEMENT_STARTED,
		MESSAGE_DEFINITION_STARTED,
		MESSAGE_DEFINITION,
		BINARY_STARTED,
		BINARY_CONTENT,
		BINARY_DONE
	}

	public NavajoEventTypes type() {
		return this.type;
	}
	
	public Object attribute(String name) {
		if(attributes==null) {
			return null;
		}
		return attributes.get(name);
 	}
	
	public Object attribute(String name, Callable<Object> defaultValue) {
		if(attributes==null) {
			try {
				return defaultValue.call();
			} catch (Exception e) {
				logger.error("Error: ", e);
				return null;
			}
		}
		Object object = attributes.get(name);
		if(object==null) {
			try {
				return defaultValue.call();
			} catch (Exception e) {
				logger.error("Error: ", e);
				return null;
			}
		}
		return object;
 	}
	
	public Map<String,Object> attributes() {
		return attributes;
	}
	
	public String path() {
		return path;
	}

	public Object body() {
		return body;
	}
	
	public NavajoStreamEvent withAttribute(String key, Object value) {
		Map<String,Object> attr = new HashMap<>(this.attributes);
		attr.put(key, value);
		Collections.unmodifiableMap(attr);
		return new NavajoStreamEvent(this.path,this.type,this.body,attr); 
	}
	
	public NavajoStreamEvent withAttributes(Map<String,Object> attributes) {
		if(attributes.isEmpty()) {
			return this;
		}
		if(this.attributes.isEmpty()) {
			return new NavajoStreamEvent(this.path,this.type,this.body,attributes ); 
		}
		Map<String,Object> join = new HashMap<>(this.attributes);
		join.putAll(attributes);
		return new NavajoStreamEvent(this.path,this.type,this.body,Collections.unmodifiableMap(join) ); 
	}

	public NavajoStreamEvent removeAttribute(String attribute) {
		Map<String,Object> res = new HashMap<>(this.attributes);
		res.remove(attribute);
		return new NavajoStreamEvent(this.path,this.type,this.body,Collections.unmodifiableMap(res) ); 
	}
	
	public Msg message() throws NavajoStreamEventException {
		if(type!=NavajoEventTypes.MESSAGE && type!=NavajoEventTypes.ARRAY_ELEMENT && type!=NavajoEventTypes.MESSAGE_DEFINITION) {
			throw new NavajoStreamEventException("Wrong kind of event to query message: "+type);
		}
		return (Msg)body;
	}
}
