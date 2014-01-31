package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;
import java.util.logging.Level;

import navajocore.Version;

import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.util.AuditLog;

public class TribeManagerFactory {

	private static volatile TribeManagerInterface instance = null;
	private static Object semaphore = new Object();
	private static boolean tribeManagerFound = false;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TribeManagerFactory.class);
	
	public static void useTestVersion() {
		instance = new DummyTribeManager();
	}
	
	public static TribeManagerInterface getInstance() {

		if (instance == null ) {
			
			if ( !Version.osgiActive() ) {
				synchronized (semaphore) {
					if ( instance == null ) {
						instance = getTribeManagerService();
						if(instance == null || instance instanceof DummyTribeManager) {
							tribeManagerFound = false;
						} else {
							tribeManagerFound = true;
						}
					}
				}
			}
		}
		return instance;

	}

	private static TribeManagerInterface getTribeManagerService() {
		
			try {
				Object value = DispatcherFactory.getInstance().getNavajoConfig().getParameter("useCluster"); // getMessage("parameters").getProperty("isLegacyMode");
				if (value != null) {
					if(value instanceof Boolean) {
						Boolean b =  (Boolean)value;
						if(!b) {
							logger.warn("Hazelcast is disabled");
							return new DummyTribeManager();
						}
						logger.info("Hazelcast enabled");
					}
					logger.warn("Bad hazelcast type");
				}

				Class<? extends TribeManagerInterface> c = (Class<? extends TribeManagerInterface>) 
				Class.forName("com.dexels.navajo.hazelcast.tribe.HazelcastTribeManager");
				TribeManagerInterface dummy = c.newInstance();
				Method m = c.getMethod("configure", (Class[]) null);
				m.invoke(dummy, (Object[])null);
				return dummy;
			} catch (Throwable e) {
				logger.error("Could not start Tribe Manager", e);
				return new DummyTribeManager();
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

	public static void setInstance(TribeManagerInterface tm) {
		instance = tm;
	}
}
