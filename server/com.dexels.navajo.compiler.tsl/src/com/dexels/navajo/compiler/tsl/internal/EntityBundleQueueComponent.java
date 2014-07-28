package com.dexels.navajo.compiler.tsl.internal;


import java.io.File;
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
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;

public class EntityBundleQueueComponent implements EventHandler, BundleQueue {
	
	private static final String ENTITIES_FOLDER  = "entities/";
	private BundleCreator bundleCreator = null;
	private ExecutorService executor;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EntityBundleQueueComponent.class);
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}
	
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	public void activate() {
		this.executor = Executors.newFixedThreadPool(1);
	}
	
	public void deactivate() {
		executor.shutdown();
		executor = null;
	}
	
	public void enqueueEntity(final String entity, final String extension) {
		executor.execute(new Runnable(){

			@Override
			public void run() {
				List<String> failures = new ArrayList<String>();
				List<String> success = new ArrayList<String>();
				List<String> skipped = new ArrayList<String>();
				logger.info("Eagerly compiling: "+entity);
				try {
					bundleCreator.createBundle(entity, new Date(), extension, failures, success, skipped, false, false, true);
					if(!skipped.isEmpty()) {
						logger.info("Entity compilation skipped: "+entity);
					}
					if(!failures.isEmpty()) {
						logger.info("Entity compilation failed: "+entity);
					}
					
					// Entities are installed immediately
					logger.info("Installing entity : "+entity);
					bundleCreator.installBundles(entity, failures, success, skipped, true, extension);
					
				} catch (Throwable e) {
					logger.error("Error: ", e);
				}
			}});
	}


	@Override
	public void handleEvent(Event e) {
		RepositoryInstance ri =  (RepositoryInstance) e.getProperty("repository");
		List<String> changedEntities = RepositoryEventParser.filterChanged(e, ENTITIES_FOLDER);
		for (String changedEntity : changedEntities) {
			try {
				File location = new File(ri.getRepositoryFolder(),changedEntity);
				if(location.isFile()) {
					extractEntity(changedEntity);
				}
			} catch (IllegalArgumentException e1) {
				logger.warn("Error: ", e1);
			}
		}
	}

	private void extractEntity(String changedEntity) {
		String stripped = changedEntity.substring(ENTITIES_FOLDER.length());
		int dotIndex = stripped.lastIndexOf(".");
		if(dotIndex<0) {
			throw new IllegalArgumentException("Entities need an extension, and "+changedEntity+" has none. Ignoring.");
		}
		String entityName = stripped.substring(0,dotIndex);
		String extension = stripped.substring(dotIndex,stripped.length());

		logger.debug("Entityname: "+entityName);
		logger.debug("extension: "+extension);
		if(".rptdesign".equals(extension)) {
			logger.info("Ignoring report "+entityName);
			return;
		}
		enqueueEntity(entityName,extension);
	}

	@Override
	public void enqueueScript(String script, String extension) {
		enqueueEntity(script, extension);
		
	}


}
