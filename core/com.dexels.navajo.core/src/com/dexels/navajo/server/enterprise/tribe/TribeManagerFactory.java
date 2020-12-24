/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherFactory;

import navajocore.Version;

public class TribeManagerFactory {

    private static final Logger logger = LoggerFactory.getLogger(TribeManagerFactory.class);

	private static volatile TribeManagerInterface instance = null;

	private static Object semaphore = new Object();

	public static TribeManagerInterface getInstance() {

		if (instance == null ) {
			if (!Version.osgiActive() ) {
				synchronized (semaphore) {
					if (instance == null) {
						instance = getTribeManagerService();
					}
				}
			}
		}

		return instance;
	}

	private static TribeManagerInterface getTribeManagerService() {

		try {
			Object value = DispatcherFactory.getInstance().getNavajoConfig().getParameter("useCluster");
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

			Class<?> c = Class.forName("com.dexels.navajo.hazelcast.tribe.HazelcastTribeManager");
			TribeManagerInterface dummy = (TribeManagerInterface) c.getDeclaredConstructor()
			        .newInstance();
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
		i.terminate();
	}

	public static synchronized void setInstance(TribeManagerInterface tm) {
		logger.info("Setting TribeManagerFactory: {}",tm);
		if(tm==null) {
			logger.error("Wololo Setting tribemanager to null. Could be a shutdown? ", new Exception());
		}
		instance = tm;
	}
}
