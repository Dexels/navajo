package com.dexels.navajo.document.stream.events;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NavajoStreamEvent {
	protected final NavajoEventTypes type;
	private final Map<String, Object> attributes;
	private final String path;
	private final Object body;
	
	public NavajoStreamEvent(String path, NavajoEventTypes type, Object body, Map<String,Object> attributes) {
		this.type = type;
		this.path = path;
		this.body = body;
		this.attributes = Collections.unmodifiableMap(attributes);
	}

	
	public enum NavajoEventTypes {
		MESSAGE,HEADER,ARRAY_START,ARRAY_ELEMENT,ARRAY_DONE,NAVAJO_DONE
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
	
	public String getPath() {
		return path;
	}

	public Object getBody() {
		return body;
	}
	
	private NavajoStreamEvent copy() {
		return new NavajoStreamEvent(this.path,this.type,this.body,attributes); 
	}
	
	public NavajoStreamEvent withAttribute(String key, Object value) {
		Map<String,Object> attr = new HashMap<>(this.attributes);
		attr.put(key, value);
		Collections.unmodifiableMap(attr);
		return new NavajoStreamEvent(this.path,this.type,this.body,attr); 
	}
//	public NavajoStreamEvent with 
}
