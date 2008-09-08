package com.dexels.navajo.server.enterprise.queue;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;


public class RequestResponseQueueFactory {

	private static volatile RequestResponseQueueInterface instance = null;
	private static Object semaphore = new Object();
	
	@SuppressWarnings("unchecked")
	public static RequestResponseQueueInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						
						Class c = Class.forName("com.dexels.navajo.adapter.queue.RequestResponseQueue");
						RequestResponseQueueInterface dummy = (RequestResponseQueueInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (RequestResponseQueueInterface) m.invoke(dummy, null);
					} catch (Exception e) {
						e.printStackTrace(System.err);
						AuditLog.log("INIT", "Queueable adapters not available", Level.WARNING);
						instance = new DummyRequestResponseQueue();
					}	
				}
				
				return instance;
			}
		}
		
	}
}
