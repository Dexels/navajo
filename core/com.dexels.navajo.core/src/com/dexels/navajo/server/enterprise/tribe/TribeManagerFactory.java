package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;
import java.util.logging.Level;

import navajocore.Version;

import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.util.AuditLog;

public class TribeManagerFactory {

	private static volatile TribeManagerInterface instance = null;
	private static Object semaphore = new Object();
	private static volatile boolean tribeManagerFound = false;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TribeManagerFactory.class);
	
	public static void useTestVersion() {
		instance = new DummyTribeManager();
	}
	
	public static TribeManagerInterface getInstance() {
		
		if ( instance != null ) {
			return instance;
		} else {
			synchronized (semaphore) {
				if ( instance == null ) {
					instance = getTribeManagerService();
					if(instance==null || instance instanceof DummyTribeManager) {
						tribeManagerFound = false;
					} else {
						tribeManagerFound = true;
					}
				}
				return instance;
			}
		}
		
	}

	private static TribeManagerInterface getTribeManagerService() {
		if(Version.osgiActive()) {
			return getOSGiTribeManagerService();
		}
			try {
				Class<? extends TribeManagerInterface> c = (Class<? extends TribeManagerInterface>) Class.forName("com.dexels.navajo.enterprise.cluster.TribeManager");
				TribeManagerInterface dummy = c.newInstance();
				Method m = c.getMethod("getInstance", (Class[]) null);
				return (TribeManagerInterface) m.invoke(dummy, (Object[])null);
			} catch (Throwable e) {
				logger.error("Could not start Tribe Manager", e);
				return new DummyTribeManager();
			}	
	}
	

	public static TribeManagerInterface getOSGiTribeManagerService() {
			ServiceReference<TribeManagerInterface> sr = Version.getDefaultBundleContext().getServiceReference(TribeManagerInterface.class);
			if(sr!=null) {
				// TODO unsure of this
				TribeManagerInterface tmi = Version.getDefaultBundleContext().getService(sr);
				Version.getDefaultBundleContext().ungetService(sr);
				return tmi;
			} else {
				logger.warn("No tribe manager found!");
				return null;
			}
}

	
	public static void startStatusCollector() {
		if ( instance != null && tribeManagerFound ) {
			// Startup tribal status collector.
			try {
				Class<?> c = Class.forName("com.dexels.navajo.tribe.impl.TribalStatusCollector");
				Object dummy = c.newInstance();
				Method m = c.getMethod("getInstance",(Class[]) null);
				m.invoke(dummy, (Object[])null);
			} catch (Throwable e) {
				//e.printStackTrace(System.err);
				AuditLog.log("INIT", "WARNING: Tribe Status Collector not available", Level.WARNING);
			}	
		}
	}

	public static final void shutdown() {
		if(instance==null) {
			return;
		}
		instance.terminate();
		instance = null;
	}
}
