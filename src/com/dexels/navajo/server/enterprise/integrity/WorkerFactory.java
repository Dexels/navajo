package com.dexels.navajo.server.enterprise.integrity;

import java.lang.reflect.Method;

public class WorkerFactory {

	private static volatile WorkerInterface instance = null;
	private static Object semaphore = new Object();
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static final WorkerInterface getInstance() {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.integrity.Worker");
						WorkerInterface dummy = (WorkerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (WorkerInterface) m.invoke(dummy, null);
					} catch (Exception e) {
						System.err.println("WARNING: Integrity Worker not available");
						instance = new DummyWorker();
					}	
				}
				
				return instance;
			}
		}
	}
}
