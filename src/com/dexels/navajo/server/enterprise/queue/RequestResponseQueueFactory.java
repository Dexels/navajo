package com.dexels.navajo.server.enterprise.queue;

import java.lang.reflect.Method;


public class RequestResponseQueueFactory {

	private static volatile RequestResponseQueueInterface instance = null;
	private static Object semaphore = new Object();
	
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
						System.err.println("WARNING: Queueable adapters not available");
						instance = new DummyRequestResponseQueue();
					}	
				}
				
				return instance;
			}
		}
		
	}
}
