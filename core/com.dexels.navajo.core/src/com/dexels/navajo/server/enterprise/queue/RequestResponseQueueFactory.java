package com.dexels.navajo.server.enterprise.queue;

import java.lang.reflect.Method;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.util.AuditLog;


public class RequestResponseQueueFactory {

	private static volatile RequestResponseQueueInterface instance = null;
	private static Object semaphore = new Object();
	
	private final static Logger logger = LoggerFactory
			.getLogger(RequestResponseQueueFactory.class);
	
	public static RequestResponseQueueInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						
						Class<RequestResponseQueueInterface> c = (Class<RequestResponseQueueInterface>) Class.forName("com.dexels.navajo.adapter.queue.RequestResponseQueue");
						RequestResponseQueueInterface dummy = c.newInstance();
						Method m = c.getMethod("getInstance", (Class[])null);
						instance = (RequestResponseQueueInterface) m.invoke(dummy,(Object[]) null);
					} catch (Exception e) {
						logger.error("Error getting requestresponse queue:",e);
						instance = new DummyRequestResponseQueue();
					}	
				}
				
				return instance;
			}
		}
		
	}
}
