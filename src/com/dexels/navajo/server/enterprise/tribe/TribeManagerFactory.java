package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;

public class TribeManagerFactory {

	private static volatile TribeManagerInterface instance = null;
	private static Object semaphore = new Object();
	private static volatile boolean tribeManagerFound = false;
	
	public static void useTestVersion() {
		instance = new DummyTribeManager();
	}
	
	public static TribeManagerInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				if ( instance == null ) {
				
					instance = getTribeManagerService();
					if(instance==null || instance instanceof DummyTribeManager) {
						tribeManagerFound = false;
					} else {
						tribeManagerFound = true;
					}
				}
				return instance;
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public static TribeManagerInterface getTribeManagerService() {
			try {
				/**
				 * These constructions will never work in an OSGi-like environment.
				 * TODO rewrite 
				 */
				Class<? extends TribeManagerInterface> c = (Class<? extends TribeManagerInterface>) Class.forName("com.dexels.navajo.tribe.TribeManager");
				TribeManagerInterface dummy = c.newInstance();
				Method m = c.getMethod("getInstance", (Class[]) null);
				return (TribeManagerInterface) m.invoke(dummy, (Object[])null);
			} catch (Throwable e) {
				AuditLog.log("INIT", "WARNING: Tribe Manager not available", Level.WARNING);
				 return new DummyTribeManager();
			}	
	}
	
	public static void startStatusCollector() {
		if ( instance != null && tribeManagerFound ) {
			// Startup tribal status collector.
			try {
				Class<?> c = Class.forName("com.dexels.navajo.tribe.TribalStatusCollector");
				Object dummy = c.newInstance();
				Method m = c.getMethod("getInstance",(Class[]) null);
				m.invoke(dummy, (Object[])null);
			} catch (Throwable e) {
				//e.printStackTrace(System.err);
				AuditLog.log("INIT", "WARNING: Tribe Status Collector not available", Level.WARNING);
			}	
		}
	}

	public static final void shutdown() {
		if(instance==null) {
			return;
		}
		instance.terminate();
		instance = null;
	}
}
