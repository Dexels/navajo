package com.dexels.navajo.server.enterprise.xmpp;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.util.AuditLog;

public class JabberWorkerFactory {

	private static volatile JabberWorkerInterface instance = null;
	private static Object semaphore = new Object();
	
	
	public static void shutdown() {
		if(instance==null) {
			return;
		}
		getInstance().terminate();
		instance = null;
	}
	
	@SuppressWarnings("unchecked")
	public static JabberWorkerInterface getInstance() {

		if ( instance != null ) {
			return instance;
		} else {

			synchronized (semaphore) {

				if ( instance == null ) {
					try {
						Class c = Class.forName("com.dexels.navajo.jabber.JabberWorker");
						JabberWorkerInterface dummy = (JabberWorkerInterface) c.newInstance();
						Method m = c.getMethod("getInstance", (Class[])null);
						instance = (JabberWorkerInterface) m.invoke(dummy, (Object[])null);
						// Set Jabber parameters.
						Navajo jabber = DispatcherFactory.getInstance().getNavajoConfig().readConfig("jabber.xml");
						if ( jabber != null ) {
							String jabberServer = jabber.getProperty("/Jabber/Server").getValue();
							String jabberPort = jabber.getProperty("/Jabber/Port").getValue();
							String jabberService = jabber.getProperty("/Jabber/Service").getValue();
							String bootstrapUrl = jabber.getProperty("/Jabber/URL").getValue();
							System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> jabberServer = " + jabberServer);
							instance.configJabber(jabberServer, jabberPort, jabberService, bootstrapUrl);
						} else {
							AuditLog.log("INIT", "WARNING: Missing Jabber configuration", Level.WARNING);
							instance = new DummyJabberWorker();
						}
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
