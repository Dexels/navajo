package com.dexels.osgicompiler.internal;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompilerRefresher {

	private ConfigurationAdmin configAdmin;
	private Thread refreshThread;
	private final RefreshRun refreshRun = new RefreshRun();
	private boolean running = false;
	private int refreshCount = 0;

	private final static Logger logger = LoggerFactory
			.getLogger(CompilerRefresher.class);
	private static final int SLEEP_TIME = 1000000000;
	private static final String SERVICE_PID = "navajo.compiler.tsl.java.internal";

	private class RefreshRun implements Runnable {

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					//
				}
				try {
					logger.info("Refreshing compiler.");
					refreshCompilerConfig();
					logger.info("Compiler config updated.");
				} catch (Throwable e) {
					logger.error("Error refreshing compiler: ", e);
				}
			}
		}

	}

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}

	public void activate(Map<String, Object> settings) {
		refreshThread = new Thread(refreshRun);
		running = true;
		refreshThread.start();
	}

	private void refreshCompilerConfig() throws IOException {
		Configuration config = configAdmin.getConfiguration(SERVICE_PID);
		Dictionary<String, Object> settings = new Hashtable<String, Object>();
		settings.put("refreshCount", ++refreshCount);
		config.update(settings);
	}

	public void deactivate() {
		this.running = false;
		if (refreshThread != null) {
			refreshThread.interrupt();
		}
	}
}
