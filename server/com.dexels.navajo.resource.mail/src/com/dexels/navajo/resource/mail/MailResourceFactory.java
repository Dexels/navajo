package com.dexels.navajo.resource.mail;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailResourceFactory {

	private static MailResourceFactory instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MailResourceFactory.class);
	
	private final Map<String,MailResource> mailResource = new HashMap<String, MailResource>();
	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}
	
	public static MailResourceFactory getInstance() {
		return instance;
	}
	public void addMailResource(MailResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not register mail resource: It has no name.");
			return;
		}
		mailResource.put(name, resource);
	}

	public void removeMailResource(MailResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not deregister mail resource: It has no name.");
			return;
		}
		mailResource.remove(name);
	}
	
	public MailResource getMailResource(String name) {
		return mailResource.get(name);
	}
}
