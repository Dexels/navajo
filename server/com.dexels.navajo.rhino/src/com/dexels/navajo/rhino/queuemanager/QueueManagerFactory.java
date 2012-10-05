package com.dexels.navajo.rhino.queuemanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;

public class QueueManagerFactory {
	private static QueueManager instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(QueueManagerFactory.class);
	
	public synchronized static QueueManager getInstance() {
		logger.warn("In static QueueManager getter, should only happen in non-OSGi");
		if(instance==null) {
			instance = new QueueManagerImpl();
		}
		return instance;
	}
	
	public synchronized static void clearInstance() {
		if(instance!=null) {
			instance.flushCache();
			instance = null;
		}
	}

}
