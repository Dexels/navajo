package com.dexels.navajo.camel.smtpserver;

import org.apache.james.protocols.api.logger.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolLogger implements Logger {

	
	private final org.slf4j.Logger logger;
	
	public ProtocolLogger(Class<?> wrapped) {
		logger =  LoggerFactory
				.getLogger(wrapped);
	}
	@Override
	public void debug(String msg) {
		logger.info(msg);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.info(msg,t);

	}

	@Override
	public void error(String msg) {
		logger.error(msg);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.error(msg,t);
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.info(msg,t);
	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void trace(String msg) {
		logger.info(msg);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger.info(msg,t);
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger.trace(msg,t);
	}

}
