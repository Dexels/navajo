package com.dexels.navajo.server.enterprise.scheduler;

import java.lang.reflect.Method;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.util.AuditLog;

public class WebserviceListenerFactory {

	private static final Logger logger = LoggerFactory.getLogger(WebserviceListenerFactory.class);
	
	private static volatile WebserviceListenerRegistryInterface instance = null;
	
	public static void setInstance(WebserviceListenerRegistryInterface instance) {
		WebserviceListenerFactory.instance = instance;
	}

	private static Object semaphore = new Object();
	
	public static final WebserviceListenerRegistryInterface getInstance() {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class<WebserviceListenerRegistryInterface> c = (Class<WebserviceListenerRegistryInterface>) Class.forName("com.dexels.navajo.scheduler.WebserviceListenerRegistry");
						WebserviceListenerRegistryInterface dummy = c.newInstance();
						Method m = c.getMethod("getInstance", (Class<?>[])null);
						instance = (WebserviceListenerRegistryInterface) m.invoke(dummy, (Object[])null);
					} catch (Exception e) {
						logger.error("Could not start WebserviceListener", e);
						instance = new DummyWebserviceListener();
					}	
				}
				
				return instance;
			}
		}
	}
	
}
