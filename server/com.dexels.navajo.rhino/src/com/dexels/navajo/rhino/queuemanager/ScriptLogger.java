package com.dexels.navajo.rhino.queuemanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptLogger {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ScriptLogger.class);
	
	public void log(String log) {
		logger.info("SCRIPT>"+log);
	}
}
