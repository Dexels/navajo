package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;

public class TribeManagerFactory {

	private static volatile TribeManagerInterface instance = null;
	private static Object semaphore = new Object();
	
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
					} catch (Exception e) {
						System.err.println("WARNING: Tribe Manager not available");
						instance = new DummyTribeManager();
					}	
				}
				
				return instance;
			}
		}
		
	}
}
