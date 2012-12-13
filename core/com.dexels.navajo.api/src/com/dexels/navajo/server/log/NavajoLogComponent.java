package com.dexels.navajo.server.log;

import java.util.HashMap;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavajoLogComponent implements LogListener {
	private LogService logService;
	HashMap<Long, Logger> loggers = new HashMap<Long,Logger>();

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public void clearLogService(LogService logService) {
		this.logService = null;
	}
	
	private LogReaderService logReaderService = null;
	
	
	public LogReaderService getLogReaderService() {
		return logReaderService;
	}


	public void setLogReaderService(LogReaderService logReaderService) {
		this.logReaderService = logReaderService;
	}

	public void clearLogReaderService(LogReaderService logReaderService) {
		this.logReaderService = null;
	}
	public void activate() {
		try {
			System.err.println("Logger activating");
			logReaderService.addLogListener(this);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void deactivate() {
		try {
			System.err.println("Logger deactivating");
			logReaderService.removeLogListener(this);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void logged(LogEntry log) {
		if ((log.getBundle() == null)
				|| (log.getBundle().getSymbolicName() == null)) {
			return;
		}

		// Retrieve a Logger object, or create it if none exists.
		Logger logger = loggers.get(log.getBundle().getBundleId());
		if (logger == null) {
			logger = LoggerFactory.getLogger(log.getBundle().getSymbolicName());
			loggers.put(log.getBundle().getBundleId(), logger);
		}

		// If there is an exception available, use it, otherwise just log
		// the message
		if (log.getException() != null) {
			switch (log.getLevel()) {
			case LogService.LOG_DEBUG:
				logger.debug(log.getMessage(), log.getException());
				break;
			case LogService.LOG_INFO:
				logger.info(log.getMessage(), log.getException());
				break;
			case LogService.LOG_WARNING:
				logger.warn(log.getMessage(), log.getException());
				break;
			case LogService.LOG_ERROR:
				logger.error(log.getMessage(), log.getException());
				break;
			}
		} else {
			switch (log.getLevel()) {
			case LogService.LOG_DEBUG:
				logger.debug(log.getMessage());
				break;
			case LogService.LOG_INFO:
				logger.info(log.getMessage());
				break;
			case LogService.LOG_WARNING:
				logger.warn(log.getMessage());
				break;
			case LogService.LOG_ERROR:
				logger.error(log.getMessage());
				break;
			}
		}
	}
}
