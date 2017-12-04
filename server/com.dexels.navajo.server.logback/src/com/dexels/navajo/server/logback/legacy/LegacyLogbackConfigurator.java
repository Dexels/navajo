package com.dexels.navajo.server.logback.legacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.dexels.navajo.server.api.NavajoServerContext;


public class LegacyLogbackConfigurator {

	
	
	private final static Logger logger = LoggerFactory
			.getLogger(LegacyLogbackConfigurator.class);
	private NavajoServerContext navajoServerContext;

	
	public void setNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = navajoServerContext;
	}
	
	/**
	 * 
	 * @param navajoServerContext the navajoServerContext to remove
	 */
	public void clearNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = null;
	}

	
	
	public void activate() {
		try {
			String path = navajoServerContext.getInstallationPath();
			File install = new File(path);
			File joranFile = new File(install,"config/logback.xml");
			if(!joranFile.exists()) {
				return; //
			}

			FileInputStream joranFis = null;
			try {
				 joranFis = new FileInputStream(joranFile);
				 loadLogbackConfig(joranFis,path);
			} catch (FileNotFoundException e) {
				logger.error("Error: ", e);
			} finally {
				if(joranFis!=null) {
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
	
	private void loadLogbackConfig(InputStream is, String path) {
		final ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
		if(!(iLoggerFactory instanceof LoggerContext)) {
			logger.warn("Can not configure legacy logback, the LoggerFactory is not a logback factory");
			return;
		}
		LoggerContext lc =(LoggerContext)iLoggerFactory;
	      lc.reset(); 

		StatusPrinter.print(lc);
	    try {
		  lc.putProperty("rootPath", path);
		  JoranConfigurator configurator = new JoranConfigurator();
	      configurator.setContext(lc);
	      // the context was probably already configured by default configuration 
	      // rules
	      configurator.doConfigure(is);
	      StatusPrinter.print(lc);
	      
	    } catch (JoranException je) {
	    	logger.error("Error: ", je);
	    }

	}
	
	public void deactivate() {
	}

}
