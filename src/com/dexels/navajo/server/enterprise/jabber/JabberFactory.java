package com.dexels.navajo.server.enterprise.jabber;

import java.lang.reflect.*;
import java.util.logging.*;

import com.dexels.navajo.util.*;

public class JabberFactory {
	private static volatile JabberInterface instance = null;
	private static Object semaphore = new Object();
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	
	public static final JabberInterface getInstance() {
		return getInstance("com.dexels.navajo.jabber.JabberWorker");
	}
	
	@SuppressWarnings("unchecked")
	public static final JabberInterface getInstance(String className) {
		
		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {
				
				if ( instance == null ) {
					try {
						Class c = Class.forName(className);
						JabberInterface dummy = (JabberInterface) c.newInstance();
						Method m = c.getMethod("getInstance", (Class []) null);
						instance = (JabberInterface) m.invoke(dummy, (Object []) null);
						AuditLog.log(AuditLog.AUDIT_MESSAGE_MONITOR, "Found jabber Agent: " + className);
					} catch (Throwable e) {
						AuditLog.log("INIT", "WARNING: Monitoring Agent not available: " + className, Level.WARNING);
						instance = new DummyJabberAgent();
					}	
				}
				
				return instance;
			}
		}
		
		
		
	}

}
