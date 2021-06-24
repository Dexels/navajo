/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
						Class<? extends AgentInterface> c = (Class<? extends AgentInterface>) Class.forName(className);
						AgentInterface dummy = c.getDeclaredConstructor().newInstance();
						Method m = c.getMethod("getInstance", (Class []) null);
						instance = (AgentInterface) m.invoke(dummy, (Object []) null);
					} catch (Throwable e) {
						instance = new DummyAgent();
					}	
				}
				
				return instance;
			}
		}
	}

	public static final void shutdown() {
		if(instance==null) {
			return;
		}
		instance.stop();
		instance = null;
	}
}
