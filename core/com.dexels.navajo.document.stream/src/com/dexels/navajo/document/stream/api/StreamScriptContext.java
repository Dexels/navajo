package com.dexels.navajo.document.stream.api;

import java.util.Map;

public class StreamScriptContext {

	public final String tenant;
	public final String service;
	public final String username;
	public final String password;
	
	public final Map<String, Object> attributes;

	public StreamScriptContext(String tenant, String service, String username, String password, Map<String,Object> attributes) {
		this.tenant = tenant;
		this.service = service;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
	}
}
