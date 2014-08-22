package com.dexels.navajo.karaf.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureSynchronizer implements Runnable {
	private FeaturesService featureService;
	private Map<String, Object> settings;
	private boolean running = true;
	private Properties propertyFile = new Properties();
	
	private final static Logger logger = LoggerFactory
			.getLogger(FeatureSynchronizer.class);
	
	private Thread updateThread = null;
	private String cacheDir = null;
	
	public void setFeaturesService(FeaturesService featureService) {
		this.featureService = featureService;
	}
	
	public void clearFeaturesService(FeaturesService featureService) {
		this.featureService = null;
	}
	
	
	public void activate(Map<String,Object> settings) throws IOException {
		this.settings = settings;
		updateThread = new Thread(this);
		this.cacheDir  = (String) settings.get("cacheDir");
		loadOwnedFeatures();
		this.running = true;
		updateThread.start();
		logger.info("Sync started!");
	}

	private void loadOwnedFeatures() throws FileNotFoundException, IOException {
		File cacheFolder = new File(cacheDir);
		if(!cacheFolder.exists()) {
			cacheFolder.mkdirs();
		}
		File ownershipFile = new File(cacheFolder,"ownedfeatures.properties");
		if(ownershipFile.exists()) {
			try(FileReader fr = new FileReader(ownershipFile)) {
				propertyFile.load(fr);
			}
			
		}
	}
	
	private void saveOwnedFeatures() throws FileNotFoundException, IOException {
		File cacheFolder = new File(cacheDir);
		File ownershipFile = new File(cacheFolder,"ownedfeatures.properties");
		try (FileWriter fw = new FileWriter(ownershipFile)) {
			propertyFile.store(fw, "Owned by the feature synchronizer");
		}
	}

	public void deactivate() {
		this.running = false;
		updateThread.interrupt();
	}
	
	public synchronized void updateFeatures() throws IOException {
		logger.debug("Scanning for feature updates");
		String installed = (String) settings.get("installed");
		String uninstalled = (String) settings.get("uninstalled");
		if(installed!=null) {
			String[] ins = installed.split(",");
			for (String in : ins) {
				ensureInstalled(in.trim());
			}
		}
		if(uninstalled!=null) {
			String[] uns = uninstalled.split(",");
			for (String in : uns) {
				ensureUninstalled(in);
			}
		}
	}

	private void ensureInstalled(String feature) {
		logger.debug("Ensuring installation of: "+feature);
		try {
			Feature f = featureService.getFeature(feature);
			if(f==null) {
				logger.warn("Should install: {}, but it seems unavailable. Can't be helped now, will keep trying.",feature);
			} else {
				
//				logger.info("Getting install>"+f.getInstall()+"< id: >"+f.getId()+"<");
				if(featureService.isInstalled(f)) {
					logger.debug("Feature: {} is installed. Good.",feature);
				} else {
					logger.info("Feature {} is not installed, installing.",feature);
					featureService.installFeature(feature);
				}
				
			}
			saveOwnedFeatures();
		} catch (Exception e2) {
			logger.error("Error: ", e2);
		}
	}
	
	private void ensureUninstalled(String feature) {
		try {
			Feature f = featureService.getFeature(feature);
			if(f==null) {
				logger.debug("Want to uninstall feature {}, but seems unavailable. Assuming all is well.");
			} else {
				
				if(featureService.isInstalled(f)) {
					logger.info("Feature {} is installed, uninstalling. ",feature);
					featureService.uninstallFeature(feature);
				} else {
					logger.debug("Feature: {} is not installed: ok",feature);
				}
				
			}
		} catch (Exception e2) {
			logger.error("Error: ", e2);
		}
	}


	@Override
	public void run() {
		while(running) {
			try {
				updateFeatures();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
		}
	}

}
