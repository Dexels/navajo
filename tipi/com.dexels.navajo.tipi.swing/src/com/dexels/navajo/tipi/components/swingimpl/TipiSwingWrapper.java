package com.dexels.navajo.tipi.components.swingimpl;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.MainApplication;
import tipiswing.SwingTipiApplicationInstance;

import com.dexels.navajo.tipi.TipiException;

public class TipiSwingWrapper {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingWrapper.class);
	
	public static SwingTipiApplicationInstance runApp(BundleContext bundle, String appInstance) {
		try {
			SwingTipiApplicationInstance applicationInstance = MainApplication.runApp(bundle, appInstance);
			applicationInstance.setBundleContext(bundle);
			applicationInstance.setCurrentContext(applicationInstance.createContext());
			return applicationInstance;
		} catch (IOException e) {
			logger.error("Error detected",e);
		} catch (TipiException e) {
			logger.error("Error detected",e);
		}
		return null;
	}

	public static SwingTipiApplicationInstance runApp(BundleContext bc, String installationPath,String deploy,String profile) {
		try {
			SwingTipiApplicationInstance applicationInstance = MainApplication.runApp(bc, installationPath,deploy,profile);
			applicationInstance.setBundleContext(bc);
			applicationInstance.setCurrentContext(applicationInstance.createContext());
			return applicationInstance;
		} catch (IOException e) {
			logger.error("Error detected",e);
		}
		return null;
	}
}
