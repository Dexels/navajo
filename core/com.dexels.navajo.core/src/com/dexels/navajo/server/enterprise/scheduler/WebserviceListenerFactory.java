package com.dexels.navajo.server.enterprise.scheduler;

public class WebserviceListenerFactory {

	private static volatile WebserviceListenerRegistryInterface instance = null;
	
	public static void setInstance(WebserviceListenerRegistryInterface instance) {
		WebserviceListenerFactory.instance = instance;
	}
	
	public static final WebserviceListenerRegistryInterface getInstance() {
			return instance;
	}
	
}
