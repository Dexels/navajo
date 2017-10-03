package com.dexels.navajo.document.stream.api;

import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Navajo;

public class StreamScriptContext {

	public final String tenant;
	public final String service;
	public final String username;
	public final String password;
	private Navajo input = null;
	public final Map<String, Object> attributes;

	public StreamScriptContext(String tenant, String service, String username, String password, Map<String,Object> attributes) {
		this.tenant = tenant;
		this.service = service;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
	}
	
	public Optional<Navajo> getInput() {
		return Optional.of(this.input);
	}

	public void setNavajo(Navajo input) {
		this.input = input;
	}
}
