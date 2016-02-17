package com.dexels.navajo.document.stream.events;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;

public class NavajoStreamEvent {
	protected final NavajoEventTypes type;
	private final Map<String, Object> attributes;
	private final String path;
	private final Object body;
	
	NavajoStreamEvent(String path, NavajoEventTypes type, Object body, Map<String,Object> attributes) {
		this.type = type;
		this.path = path;
		this.body = body;
		this.attributes = Collections.unmodifiableMap(attributes);
	}

	public String toString() {
		if(type==NavajoEventTypes.NAVAJO_STARTED) {
			NavajoHead h = (NavajoHead)body;
			return "Type: "+type+" path: "+path+" attributes: {"+attributes+" - RPCNAME: "+h.name();
		}
		if(type==NavajoEventTypes.MESSAGE) {
			List<Prop> contents = (List<Prop>) body;
			StringBuilder sb = new StringBuilder("Message detected. Name: "+path+"\n");
			for (Prop prop : contents) {
				sb.append("Prop: "+prop.name()+" = "+prop.value()+ "with direction: "+ prop.direction() + "\n");
			}
			return sb.toString();
		}
		return "Type: "+type+" path: "+path+" attributes: {"+attributes+"}";
	}
	
	public enum NavajoEventTypes {
		MESSAGE_STARTED,MESSAGE,ARRAY_STARTED,ARRAY_ELEMENT,ARRAY_DONE,NAVAJO_DONE, NAVAJO_STARTED, ARRAY_ELEMENT_STARTED, MESSAGE_DEFINITION_STARTED, MESSAGE_DEFINITION
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
	
//	public List<String> path() {
//		if(path==null) {
//			return Collections.emptyList();
//		}
//		return Arrays.asList(path.split("/"));
//	}
//	public NavajoStreamEvent with 
}
