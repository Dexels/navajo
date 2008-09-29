package com.dexels.navajo.server.enterprise.monitoring;

import java.lang.reflect.Method;

public class AgentFactory {


	private static volatile AgentInterface instance = null;
	private static Object semaphore = new Object();
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	public static final AgentInterface getInstance() {
		return getInstance("com.dexels.navajo.server.monitoring.ZapcatZabbixAgent");
	}
	
	@SuppressWarnings("unchecked")
	public static final AgentInterface getInstance(String className) {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName(className);
						AgentInterface dummy = (AgentInterface) c.newInstance();
						Method m = c.getMethod("getInstance", (Class []) null);
						instance = (AgentInterface) m.invoke(dummy, (Object []) null);
					} catch (Throwable e) {
						//e.printStackTrace(System.err);
						instance = new DummyAgent();
					}	
				}
				
				return instance;
			}
		}
		
		
		
	}
}
