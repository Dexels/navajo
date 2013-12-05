package com.dexels.navajo.server.enterprise.xmpp;

import java.lang.reflect.Method;
import java.util.logging.Level;

import navajocore.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.util.AuditLog;

public class JabberWorkerFactory {

	private static volatile JabberWorkerInterface workerInstance = null;
	private static volatile JabberWorkerFactory instance = null;
	private static Object semaphore = new Object();
	
	private JabberWorkerInterface boundInstance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(JabberWorkerFactory.class);
	
	
	public static void shutdown() {
		if(workerInstance==null) {
			return;
		}
		getJabberWorkerInstance().terminate();
		workerInstance = null;
	}
	
	public void setJabberWorker(JabberWorkerInterface ji) {
		boundInstance = ji;
	}

	public void clearJabberWorker(JabberWorkerInterface ji) {
		boundInstance = null;
	}
	
	public static JabberWorkerFactory getInstance() {
		if(instance!=null) {
			return instance;
		}
		if(!Version.osgiActive()) {
			instance = new JabberWorkerFactory();
			return instance;
		}
		return null;
	}
	
	public static JabberWorkerInterface getJabberWorkerInstance() {
		if(Version.osgiActive()) {
			return getOSGiJabberWorker();
		}
		if ( workerInstance != null ) {
			return workerInstance;
		} else {
			synchronized (semaphore) {
				if ( workerInstance == null ) {
					workerInstance = createLegacyJabberWorker();	
				}
				return workerInstance;
			}
		}

	}
	
	private static JabberWorkerInterface getOSGiJabberWorker() {

		if(instance!=null) {
			return instance.boundInstance;
		}
		return null;
	}

	public void activate() {
		logger.debug("Jabber worker factory activated");
	}
	
	public void deactivate() {
		logger.debug("Jabber worker factory deactivated");
	}

	
	@SuppressWarnings("unchecked")
	protected static JabberWorkerInterface createLegacyJabberWorker() {
		try {
			Class c = Class.forName("com.dexels.navajo.jabber.JabberWorker");
			JabberWorkerInterface dummy = (JabberWorkerInterface) c.newInstance();
			Method m = c.getMethod("getInstance", (Class[])null);
			JabberWorkerInterface localInstance = (JabberWorkerInterface) m.invoke(dummy, (Object[])null);
			// Set Jabber parameters.
			Navajo jabber = DispatcherFactory.getInstance().getNavajoConfig().readConfig("jabber.xml");
			if ( jabber != null ) {
				String jabberServer = jabber.getProperty("/Jabber/Server").getValue();
				String jabberPort = jabber.getProperty("/Jabber/Port").getValue();
				String jabberService = jabber.getProperty("/Jabber/Service").getValue();
				String bootstrapUrl = jabber.getProperty("/Jabber/URL").getValue();
				localInstance.configJabber(jabberServer, jabberPort, jabberService, bootstrapUrl);
				return localInstance;
			} else {
				AuditLog.log("INIT", "WARNING: Missing Jabber configuration", Level.WARNING);
				return new DummyJabberWorker();
			}
		} catch (Throwable e) {
			logger.warn("INIT", "WARNING: Jabber Worker not available", e);
			return new DummyJabberWorker();
		}
	}
}
