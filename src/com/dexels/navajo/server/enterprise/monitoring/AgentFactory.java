package com.dexels.navajo.server.enterprise.monitoring;

import java.lang.reflect.Method;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;

public class AgentFactory {


	private static volatile AgentInterface instance = null;
	private static Object semaphore = new Object();
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static final AgentInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.server.monitoring.ZapcatZabbixAgent");
						WorkerInterface dummy = (WorkerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", (Class []) null);
						instance = (AgentInterface) m.invoke(dummy, (Object []) null);
					} catch (Exception e) {
						System.err.println("WARNING: Monitoring Agent not available");
						instance = new DummyAgent();
					}	
				}
				
				return instance;
			}
		}
		
		
		
	}
}
