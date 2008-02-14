package com.dexels.navajo.server.enterprise.monitoring;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;

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
						AuditLog.log(AuditLog.AUDIT_MESSAGE_MONITOR, "Found monitoring Agent: " + className);
					} catch (Throwable e) {
						//e.printStackTrace(System.err);
						AuditLog.log("INIT", "WARNING: Monitoring Agent not available: " + className, Level.WARNING);
						instance = new DummyAgent();
					}	
				}
				
				return instance;
			}
		}
		
		
		
	}
}
