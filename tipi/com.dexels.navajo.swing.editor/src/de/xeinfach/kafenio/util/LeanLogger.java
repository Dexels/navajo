package de.xeinfach.kafenio.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <P>Created on: Jan 30, 2004, 6:36:09 PM</P>
 * <P>Description: a lean logger class that supports
 * 5 diferent log levels (debug, info, warn, error and none)
 * and logs all messages to System.out.
 * </P>
 * @author Karsten Pawlik
 */
public class LeanLogger {

	public static final int DEBUG 	= 4;
	public static final int INFO 	= 3;
	public static final int WARN 	= 2;
	public static final int ERROR 	= 1;
	public static final int NONE 	= 0;
	
	private static int currentLogLevel = 1;
	private String classToLog = null;
	
	
	private final static Logger logger = LoggerFactory.getLogger(LeanLogger.class);

	/**
	 * creates a new instance of LeanLogger.
	 * @param className the classname to log.
	 */
	public LeanLogger(String className) {
		classToLog = className;
	}
	
	/**
	 * logs message as debug-message.
	 * @param message message to show.
	 */
	public void debug(String message) {
		if (getCurrentLogLevel() >= 4) {
			logger.debug("[DEBUG]: (" + classToLog + ") " + message);
		}
	}

	/**
	 * logs message as info-message.
	 * @param message message to show.
	 */
	public void info(String message) {
		if (getCurrentLogLevel() >= 3) {
			logger.info("[INFO]: (" + classToLog + ") " + message);
		}
	}
	/**
	 * logs message as info-message.
	 * @param message message to show.
	 */
	public void warn(String message) {
		if (getCurrentLogLevel() >= 2) {
			logger.warn("[WARN]: (" + classToLog + ") " + message);
		}
	}
	/**
	 * logs message as info-message.
	 * @param message message to show.
	 */
	public void error(String message) {
		if (getCurrentLogLevel() >= 1) {
			logger.error("[ERROR]: (" + classToLog + ") " + message);
		}
	}

	/**
	 * @return returns the current loglevel
	 */
	public static int getCurrentLogLevel() {
		return currentLogLevel;
	}

	/**
	 * @param i sets the current log level
	 */
	public static void setCurrentLogLevel(int i) {
		if (i >= 0 && i <= 4) {
			currentLogLevel = i;
		} else {
			logger.warn("serCurrentLogLevel: invalid argument."); 
		}
	}
}
