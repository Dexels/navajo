package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;

public class TribeManagerFactory {

	private static volatile TribeManagerInterface instance = null;
	private static Object semaphore = new Object();
	private static volatile boolean tribeManagerFound = false;
	
	@SuppressWarnings("unchecked")
	public static TribeManagerInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.tribe.TribeManager");
						TribeManagerInterface dummy = (TribeManagerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (TribeManagerInterface) m.invoke(dummy, null);
						tribeManagerFound = true;
					} catch (Throwable e) {
						//e.printStackTrace(System.err);
						AuditLog.log("INIT", "WARNING: Tribe Manager not available", Level.WARNING);
						instance = new DummyTribeManager();
					}	
				}
				
				return instance;
			}
		}
		
	}
	
	public static void startStatusCollector() {
		if ( instance != null && tribeManagerFound ) {
			// Startup tribal status collector.
			try {
				Class<?> c = (Class<?>) Class.forName("com.dexels.navajo.tribe.TribalStatusCollector");
				Object dummy = c.newInstance();
				Method m = c.getMethod("getInstance", null);
				m.invoke(dummy, null);
			} catch (Throwable e) {
				//e.printStackTrace(System.err);
				AuditLog.log("INIT", "WARNING: Tribe Status Collector not available", Level.WARNING);
			}	
		}
	}
}
