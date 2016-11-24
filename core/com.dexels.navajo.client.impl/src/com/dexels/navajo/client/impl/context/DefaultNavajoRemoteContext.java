package com.dexels.navajo.client.impl.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultNavajoRemoteContext extends NavajoRemoteContext {

	
	private final static Logger logger = LoggerFactory
			.getLogger(DefaultNavajoRemoteContext.class);
	
	public DefaultNavajoRemoteContext() {
		String server = getApplicationAttribute("NavajoServer");
		String username = getApplicationAttribute("NavajoUser");
		String password = getApplicationAttribute("NavajoPassword");
		if(server!=null && username!=null && password!=null) {
			super.setupClient(server , username, password);
		} else {
			logger.warn("Missing setters, DefaultNavajoRemoteContext needs: NavajoServer,NavajoUser,NavajoPassword");
		}
	}

	
	private String getApplicationAttribute(String key) {
		String value = null;
		value = System.getenv(key);
		if(value!=null) {
			return value;
		}
		return System.getProperty(key);
	}
	
}
