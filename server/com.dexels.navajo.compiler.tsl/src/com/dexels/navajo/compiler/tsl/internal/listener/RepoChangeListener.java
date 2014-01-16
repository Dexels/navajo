package com.dexels.navajo.compiler.tsl.internal.listener;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;

public class RepoChangeListener implements EventHandler {

	private BundleCreator bundleCreator = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RepoChangeListener.class);
	
	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	/**
	 * 
	 * @param bundleCreator the bundle creator to clear
	 */
	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

	
	@Override
	public void handleEvent(Event e) {
		logger.info("EVENT FOUND! "+e);
		for (String p : e.getPropertyNames()) {
			logger.info("Name: _"+p+" value: "+e.getProperty(p));
			
		}
	}

}
