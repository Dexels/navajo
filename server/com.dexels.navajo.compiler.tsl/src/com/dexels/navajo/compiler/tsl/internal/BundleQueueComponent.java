package com.dexels.navajo.compiler.tsl.internal;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.compiler.tsl.BundleQueue;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;

public class BundleQueueComponent implements EventHandler, BundleQueue {
	
	private static final String SCRIPTS_FOLDER = "scripts/";
	private BundleCreator bundleCreator = null;
	private ExecutorService executor;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BundleQueueComponent.class);
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	public void activate() {
		this.executor = Executors.newFixedThreadPool(1);
	}
	
	public void deactivate() {
		executor.shutdown();
		executor = null;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.internal.BundleQueue#enqueueScript(java.lang.String)
	 */
	@Override
	public void enqueueScript(final String script, final String extension, final String tenant) {
		executor.execute(new Runnable(){

			@Override
			public void run() {
				List<String> failures = new ArrayList<String>();
				List<String> success = new ArrayList<String>();
				List<String> skipped = new ArrayList<String>();
				logger.info("Eagerly compiling: "+script);
				try {
					bundleCreator.createBundle(script, new Date(), extension, failures, success, skipped, false, false, tenant);
					if(!skipped.isEmpty()) {
						logger.info("Script compilation skipped: "+script);
					}
					if(!failures.isEmpty()) {
						logger.info("Script compilation failed: "+script);
					}
				} catch (Throwable e) {
					logger.error("Error: ", e);
				}
			}});
	}
	/**
	 * 
	 * @param bundleCreator the bundlecreator to clear
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	@Override
	public void handleEvent(Event e) {
		List<String> changedScripts = RepositoryEventParser.filterChanged(e,SCRIPTS_FOLDER);
		for (String changedScript : changedScripts) {
			try {
				extractScript(changedScript);
			} catch (IllegalArgumentException e1) {
				logger.warn("Error: ", e1);
			}
		}
	}

	private void extractScript(String changedScript) {
		String stripped = changedScript.substring(SCRIPTS_FOLDER.length());
		int dotIndex = stripped.lastIndexOf(".");
		if(dotIndex<0) {
			throw new IllegalArgumentException("Scripts need an extension, and "+changedScript+" has none. Ignoring.");
		}
		String scriptName = stripped.substring(0,dotIndex);
		String extension = stripped.substring(dotIndex,stripped.length());
		int scoreIndex = scriptName.lastIndexOf("_");
		String tenant = null;
		if(scoreIndex>=0) {
			tenant = scriptName.substring(scoreIndex+1, scriptName.length());
			scriptName = scriptName.substring(0,scoreIndex);
		}
		logger.debug("scriptName: "+scriptName);
		logger.debug("extension: "+extension);
		logger.debug("tenant: "+tenant);
		enqueueScript(scriptName,extension,tenant);
	}

	public static void main(String[] args) {
		BundleQueueComponent bqc = new BundleQueueComponent();
		bqc.extractScript("scripts/InitAsync_AAP.xml");
		bqc.extractScript("scripts/InitSomething.xml");
		bqc.extractScript("scripts/somepack/InitSomething.xml");
	}
	


}
