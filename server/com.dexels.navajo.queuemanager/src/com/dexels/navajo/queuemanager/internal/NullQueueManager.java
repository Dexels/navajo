package com.dexels.navajo.queuemanager.internal;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.NavajoSchedulingException;
import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;

public class NullQueueManager implements QueueManager {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NullQueueManager.class);
	
	@Override
	public void setScriptDir(File scriptDir) {
		// ignore
	}

	@Override
	public void setQueueContext(QueueContext queueContext) {
		// ignore
	}

	@Override
	public void flushCache() {
		// ignore

	}

	@Override
	public void flushCache(String service) {
		// ignore

	}

	@Override
	public String resolve(InputContext in, String script)
			throws NavajoSchedulingException {
		// ignore
		return null;
	}
	
	public void activate() {
		logger.info("Activating null queuemanager");
	}

	public void deactivate() {
		logger.info("Deactivating null queuemanager");
	}
}
