package com.dexels.navajo.server.logback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class LogbackConfigurator {

	
	private final static Logger logger = LoggerFactory.getLogger(LogbackConfigurator.class);
	
	public void activate(Map<String, Object> settings) {
		try {
			String rootPath = (String) settings.get("rootPath");
			String logbackPath = (String) settings.get("logbackPath");
			// "config/logback.xml"
			File rootFile = new File(rootPath);
			File joranFile = new File(rootFile, logbackPath);
			if (!joranFile.exists()) {
				return; //
			}
			FileInputStream joranFis = null;
			try {
				joranFis = new FileInputStream(joranFile);
				loadLogbackConfig(joranFis, settings);
			} catch (FileNotFoundException e) {
				logger.error("Error: ", e);
			} finally {
				if (joranFis != null) {
					try {
						joranFis.close();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
				}
			}
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	private void loadLogbackConfig(InputStream is, Map<String, Object> settings) {
		final ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
		if(!(iLoggerFactory instanceof LoggerContext)) {
			logger.warn("Can not configure logback, the LoggerFactory is not a logback factory");
			return;
		}
		LoggerContext lc =(LoggerContext)iLoggerFactory;
		lc.reset();

		StatusPrinter.print(lc);
		try {
			for (Entry<String,Object> e : settings.entrySet()) {
				lc.putProperty(e.getKey(),"" + e.getValue());
			}
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			configurator.doConfigure(is);
			StatusPrinter.print(lc);

		} catch (JoranException je) {
			logger.error("Error: ", je);
		}

	}

	public void deactivate() {
		System.err.println("deactivating");
	}

}
