package com.dexels.navajo.server.enterprise.xmpp;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.util.AuditLog;

public class JabberWorkerFactory {

	private static volatile JabberWorkerInterface instance = null;
	private static Object semaphore = new Object();
	
	public static JabberWorkerInterface getInstance() {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {

				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.jabber.JabberWorker");
						JabberWorkerInterface dummy = (JabberWorkerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", null);
						instance = (JabberWorkerInterface) m.invoke(dummy, null);
					} catch (Throwable e) {
						//e.printStackTrace(System.err);
						AuditLog.log("INIT", "WARNING: Jabber Worker not available", Level.WARNING);
						instance = new DummyJabberWorker();
					}	
				}

				return instance;
			}
		}

	}
}
