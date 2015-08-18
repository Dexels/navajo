package com.dexels.navajo.server.enterprise.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebserviceListenerFactory {

	private static final Logger logger = LoggerFactory.getLogger(WebserviceListenerFactory.class);
	
	private static volatile WebserviceListenerRegistryInterface instance = null;
	
	public static void setInstance(WebserviceListenerRegistryInterface instance) {
		WebserviceListenerFactory.instance = instance;
	}
	
	public static final WebserviceListenerRegistryInterface getInstance() {
			return instance;
	}
	
}
