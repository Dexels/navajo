package com.dexels.navajo.server.enterprise.scheduler;

import java.lang.reflect.Method;

public class WebserviceListenerFactory {

	private static volatile WebserviceListenerRegistryInterface instance = null;
	private static Object semaphore = new Object();
	
	public static final WebserviceListenerRegistryInterface getInstance() {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.scheduler.WebserviceListenerRegistry");
						WebserviceListenerRegistryInterface dummy = (WebserviceListenerRegistryInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (WebserviceListenerRegistryInterface) m.invoke(dummy, null);
					} catch (Exception e) {
						System.err.println("WARNING: WebserviceListener not available");
						instance = new DummyWebserviceListener();
					}	
				}
				
				return instance;
			}
		}
	}
	
}
