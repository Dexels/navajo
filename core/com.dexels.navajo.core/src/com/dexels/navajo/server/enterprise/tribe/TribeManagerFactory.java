package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherFactory;

import navajocore.Version;

public class TribeManagerFactory {

	private static volatile TribeManagerInterface instance = null;
	private static Object semaphore = new Object();
	private static boolean tribeManagerFound = false;
	
	private static final Logger logger = LoggerFactory.getLogger(TribeManagerFactory.class);

	public static TribeManagerInterface getInstance() {

		if (instance == null ) {
			
			if ( !Version.osgiActive() ) {
				synchronized (semaphore) {
					if ( instance == null ) {
						instance = getTribeManagerService();
						if(instance == null || instance instanceof DefaultTribeManager) {
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

	@SuppressWarnings("unchecked")
	private static TribeManagerInterface getTribeManagerService() {
		
			try {
				Object value = DispatcherFactory.getInstance().getNavajoConfig().getParameter("useCluster"); // getMessage("parameters").getProperty("isLegacyMode");
				if (value != null) {
					if(value instanceof Boolean) {
						Boolean b =  (Boolean)value;
						if(!b) {
							logger.warn("Hazelcast is disabled");
							return new DefaultTribeManager();
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
				return new DefaultTribeManager();
			}	
	}
	


	public static final void shutdown() {
		logger.info("Shutting down TribeManagerFactory");
		TribeManagerInterface i = instance;
		if(i==null) {
			return;
		}
		setInstance(null);
		i.terminate();
	}

	public static synchronized void setInstance(TribeManagerInterface tm) {
		logger.info("Setting TribeManagerFactory: {}",tm);
		instance = tm;
	}
}
