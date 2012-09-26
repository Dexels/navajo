package com.dexels.navajo.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;

public class DispatcherComponent extends Dispatcher implements
		DispatcherInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(DispatcherComponent.class);
	
	public void activate() {
		logger.info("Activate dispatcher!");
	}
	public void deactivate() {
		logger.info("Deactivate dispatcher!");
	}

	public void setNavajoConfig(NavajoConfigInterface nci) {
		logger.info("setNavajoConfig dispatcher!");
		super.setNavajoConfig(nci);
	}
	public void clearNavajoConfig(NavajoConfigInterface nci) {
		logger.info("clearNavajoConfig dispatcher!");
		super.clearNavajoConfig(nci);
	}

	public void setBundleCreator(BundleCreator nci) {
		logger.info("setBundleCreator dispatcher!");
		super.setBundleCreator(nci);
	}
	public void clearBundleCreator(BundleCreator nci) {
		logger.info("clearBundleCreater dispatcher!");
		super.clearBundleCreator(nci);
	}

}
