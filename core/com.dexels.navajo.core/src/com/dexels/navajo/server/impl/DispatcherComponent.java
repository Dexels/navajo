package com.dexels.navajo.server.impl;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;

public class DispatcherComponent extends Dispatcher implements
		DispatcherInterface {

	BundleContext myContext = null;
	NavajoEventRegistry myRegistry = null;
	TribeManagerInterface tribeManager = null;
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherComponent.class);
	
	public void activate(BundleContext context) {
		try {
			logger.info("Activate dispatcher!");
			DispatcherFactory.createDispatcher(this);
			myContext = context;
			if ("true".equals(System.getenv("SIMULATION_MODE"))) {
			    simulationMode = true;
			}
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}
	public void deactivate() {
		logger.debug("Deactivate dispatcher!");
		DispatcherFactory.setInstance(null);
		myContext = null;
	}

	@Override
	public void setNavajoConfig(NavajoConfigInterface nci) {
		logger.debug("setNavajoConfig dispatcher!");
		super.setNavajoConfig(nci);
	}

	public void setTribeManager(TribeManagerInterface tmi) {
		tribeManager = tmi;
	}
	
	public void clearTribeManager(TribeManagerInterface tmi) {
		tribeManager = null;
	}
	
	public void setEventRegistry(NavajoEventRegistry ner) {
		myRegistry = ner;
	}
	
	public void clearEventRegistry(NavajoEventRegistry ner) {
		myRegistry = null;
	}
}
