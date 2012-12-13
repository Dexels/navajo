package com.dexels.navajo.server.enterprise.xmpp;

import java.lang.reflect.Method;
import java.util.logging.Level;

import navajocore.Version;

import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.util.AuditLog;

public class JabberWorkerFactory {

	private static volatile JabberWorkerInterface instance = null;
	private static Object semaphore = new Object();
	
	private final static Logger logger = LoggerFactory
			.getLogger(JabberWorkerFactory.class);
	
	
	public static void shutdown() {
		if(instance==null) {
			return;
		}
		getInstance().terminate();
		instance = null;
	}
	
	public static JabberWorkerInterface getInstance() {
		if(Version.osgiActive()) {
			return getOSGiJabberWorker();
		}
		if ( instance != null ) {
			return instance;
		} else {
			synchronized (semaphore) {
				if ( instance == null ) {
					instance = createLegacyJabberWorker();	
				}
				return instance;
			}
		}

	}
	
	private static JabberWorkerInterface getOSGiJabberWorker() {
		ServiceReference<JabberWorkerInterface> sr = Version.getDefaultBundleContext().getServiceReference(JabberWorkerInterface.class);
		if(sr==null) {
			logger.warn("No JabberWorker implementation found");
			return null;
		}
		JabberWorkerInterface result = Version.getDefaultBundleContext().getService(sr);
		Version.getDefaultBundleContext().ungetService(sr);
		return result;
	}

	public void activate() {
		
	}
	
	public void deactivate() {
		
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
