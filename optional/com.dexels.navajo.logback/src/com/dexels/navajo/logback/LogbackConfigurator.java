package com.dexels.navajo.logback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.dexels.navajo.repository.api.RepositoryManager;

public class LogbackConfigurator {

	private final static Logger logger = LoggerFactory.getLogger(LogbackConfigurator.class);

	private static final String DEFAULTPATH = "logback.xml";

//	public RepositoryManager repositoryManager;
	
	public void activate(Map<String, Object> settings) {
		try {
			URL joranURL = getSettingsFile(settings);
			if(joranURL!=null) {
				logger.debug("Using logger configfile: "+joranURL);
			}
			if (joranURL==null ) {
				logger.warn("No logback configuration file found. Using default.");
				return; //
			}
			InputStream joranFis = null;
			try {
				joranFis = joranURL.openStream();
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

	private URL getSettingsFile(Map<String, Object> settings) throws MalformedURLException, URISyntaxException {
		File joranFile = null; //new File(rootFile, logbackPath);
		String fileInstall = (String)settings.get("felix.fileinstall.filename");
		String path = (String) settings.get("logbackPath");
		if(path==null) {
			path = DEFAULTPATH;
		}
		File pathfile = new File(path);
		if(pathfile.isAbsolute()) {
			return pathfile.toURI().toURL();
		}
		
		if(fileInstall!=null) {
			URL install = new URL(fileInstall);
			File installFolder = new File(install.toURI()).getParentFile();
			joranFile = new File(installFolder,path);
			return joranFile.toURI().toURL();
		} else {
			logger.warn("Can not configure log. Using defaults");
			return null;
		}
	}


	private void loadLogbackConfig(InputStream is, Map<String, Object> settings) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
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
