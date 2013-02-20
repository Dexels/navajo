package com.dexels.navajo.server.logback.legacy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.dexels.navajo.server.api.NavajoServerContext;


public class LegacyLogbackConfigurator {

	
//	private final static Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
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
				e.printStackTrace();
			} finally {
				if(joranFis!=null) {
					try {
						joranFis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void loadLogbackConfig(InputStream is, String path) {
		LoggerContext lc =(LoggerContext)LoggerFactory.getILoggerFactory();
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
	       je.printStackTrace();
	    }

	}
	
	public void deactivate() {
		System.err.println("deactivating");
	}

}
