package com.dexels.navajo.document.stream.api;

import java.util.Map;

public class StreamScriptContext {

	private String tenant;
	private String service;
	private String username;
	private Map<String, Object> attributes;

	public StreamScriptContext(String tenant, String service, String username, Map<String,Object> attributes) {
		this.tenant = tenant;
		this.service = service;
		this.username = username;
		this.attributes = attributes;
	}

	public String tenant() {
		return tenant;
	}

	public String service() {
		return service;
	}

	public String username() {
		return username;
	}

}
