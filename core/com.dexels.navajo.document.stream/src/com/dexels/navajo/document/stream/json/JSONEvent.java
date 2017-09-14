package com.dexels.navajo.document.stream.json;

import com.fasterxml.jackson.core.JsonToken;

public class JSONEvent {

	private final JsonToken token;
	private final Object value;
	private final String name;

	public JSONEvent(JsonToken token, String name, Object value) {
		this.token = token;
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Event: "+name+" "+token+" value: "+value;
	}
	
	public JsonToken token() {
		return this.token;
	}

	
}
