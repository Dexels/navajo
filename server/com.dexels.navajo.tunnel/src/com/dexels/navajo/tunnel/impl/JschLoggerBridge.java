package com.dexels.navajo.tunnel.impl;

import org.slf4j.Logger;


public class JschLoggerBridge implements com.jcraft.jsch.Logger {

	private final Logger parent;

	public JschLoggerBridge(Logger parent) {
		this.parent = parent;
	}
	@Override
	public boolean isEnabled(int arg0) {
		return true;
	}

	@Override
	public void log(int level, String message) {
		switch (level) {
		case DEBUG:
			parent.debug(message);
			break;
		case INFO:
			parent.info(message);
			break;
		case WARN:
			parent.warn(message);
			break;
		case ERROR:
			parent.error(message);
			break;
		}
	}

}
