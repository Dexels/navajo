package com.dexels.navajo.server.enterprise.integrity;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;

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
						Class c = Class.forName("com.dexels.navajo.integrity.TribalWorker");
						WorkerInterface dummy = (WorkerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (WorkerInterface) m.invoke(dummy, null);
					} catch (Exception e) {
						AuditLog.log("INIT", "WARNING: Integrity Worker not available", Level.WARNING);
						instance = new DummyWorker();
					}	
				}
				
				return instance;
			}
		}
	}
}
