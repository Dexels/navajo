package com.dexels.navajo.karaf.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

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
	
	public void setFeatureService(FeaturesService featureService) {
		this.featureService = featureService;
	}
	
	public void clearFeatureService(FeaturesService featureService) {
		this.featureService = null;
	}
	
	public void activate(Map<String,Object> settings) throws IOException {
		this.settings = settings;
		updateThread = new Thread(this);
		this.cacheDir  = (String) settings.get("cacheDir");
		loadOwnedFeatures();
		this.running = true;
	}

	private void loadOwnedFeatures() throws FileNotFoundException, IOException {
		File cacheFolder = new File(cacheDir);
		File ownershipFile = new File(cacheFolder,"ownedfeatures.properties");
		FileReader fr = new FileReader(ownershipFile);
		propertyFile.load(fr);
		fr.close();
	}
	
	private void saveOwnedFeatures() throws FileNotFoundException, IOException {
		File cacheFolder = new File(cacheDir);
		File ownershipFile = new File(cacheFolder,"ownedfeatures.properties");
		FileWriter fw = new FileWriter(ownershipFile);
		propertyFile.store(fw, "Owned by the feature synchronizer");
		fw.close();
	}

	public void deactivate() {
		this.running = false;
		updateThread.interrupt();
	}
	
	public synchronized void updateFeatures() throws IOException {
		for (Map.Entry<String, Object> e : settings.entrySet()) {
			String feature = e.getKey();
			if(!feature.startsWith("feature.")) {
				continue;
			}
			String version = (String) e.getValue();
			String current = (String) propertyFile.get(feature);
			if(current!=null) {
				propertyFile.remove(feature);
			}
			try {
				featureService.installFeature(feature, version);
				
			} catch (Exception e1) {
				logger.error("Error installing Karaf feature: "+feature+" version: "+version, e1);
			}
		}
		saveOwnedFeatures();
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
