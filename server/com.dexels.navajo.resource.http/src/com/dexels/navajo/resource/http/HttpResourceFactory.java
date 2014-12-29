package com.dexels.navajo.resource.http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResourceFactory {

	private static HttpResourceFactory instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(HttpResourceFactory.class);
	
	private final Map<String,HttpResource> httpResource = new HashMap<String, HttpResource>();
	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}
	
	public static HttpResourceFactory getInstance() {
		return instance;
	}
	public void addHttpResource(HttpResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not register http resource: It has no name.");
			return;
		}
		httpResource.put(name, resource);
	}

	public void removeHttpResource(HttpResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not deregister http resource: It has no name.");
			return;
		}
		httpResource.remove(name);
	}
	
	public HttpResource getHttpResource(String name) {
		return httpResource.get(name);
	}
}
