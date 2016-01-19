package com.dexels.navajo.document.stream.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

	public String toString() {
		return "Type: "+type+" path: "+path+" attributes: {"+attributes+"}";
	}
	
	public enum NavajoEventTypes {
		MESSAGE_STARTED,MESSAGE,HEADER,ARRAY_STARTED,ARRAY_ELEMENT,ARRAY_DONE,NAVAJO_DONE, NAVAJO_STARTED, ARRAY_ELEMENT_STARTED, MESSAGE_DEFINITION_STARTED, MESSAGE_DEFINITION
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
	
	public NavajoStreamEvent withAttribute(String key, Object value) {
		Map<String,Object> attr = new HashMap<>(this.attributes);
		attr.put(key, value);
		Collections.unmodifiableMap(attr);
		return new NavajoStreamEvent(this.path,this.type,this.body,attr); 
	}
	
	public List<String> path() {
		if(path==null) {
			return Collections.emptyList();
		}
		return Arrays.asList(path.split("/"));
	}
//	public NavajoStreamEvent with 
}
