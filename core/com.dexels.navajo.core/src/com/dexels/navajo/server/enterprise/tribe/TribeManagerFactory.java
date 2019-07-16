package com.dexels.navajo.server.enterprise.tribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TribeManagerFactory {

	private static TribeManagerInterface instance = null;
	
	private static final Logger logger = LoggerFactory.getLogger(TribeManagerFactory.class);

	public static synchronized TribeManagerInterface getInstance() {
		return instance;
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
