package com.dexels.navajo.tipi.application;

import java.io.IOException;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingWrapper;

@SuppressWarnings({"rawtypes","unused"})

public class ApplicationComponent {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationComponent.class);
	private TipiApplicationInstance instance;

	private BundleContext bundleContext;


	private boolean isRunning = false;
	private boolean isActive = false;
	private ConfigurationAdmin configAdmin;
	
	public void activate(Map<String,Object> properties, BundleContext c) {
		this.bundleContext = c;
		logger.info("Tipi Application Active");
		if(instance!=null) {
			instance.close();
		}
		isActive = true;
		String contextPath = (String) properties.get("tipi.context");
		String deployment = (String) properties.get("deployment");
		String profile = (String) properties.get("profile");

		try {
			Configuration boot = configAdmin.getConfiguration("tipi.boot");
			if(boot.getProperties()==null) {
				logger.warn("No boot config, delaying actvation");
				String tipiBoot = System.getProperty("tipi.boot");
				if(tipiBoot!=null) {
					logger.info("Found a system var, booting after all.");
					if(contextPath!=null) {
						boot.delete();
						bootApplication(bundleContext,contextPath,deployment,profile);
					}
				}
				return;
			}
		} catch (IOException e) {
			logger.error("Getting boot config failed? ",e);
			return;
		}
		//		if(verifyOptionalDeps()) {
		if(contextPath!=null) {
			bootApplication(bundleContext,contextPath,deployment,profile);
		}
//		}
		

	}



	public void deactivate() {
		logger.info("Deactivating tipi Application");
		if(instance!=null) {
			instance.close();
			instance = null;
		}
		isActive = false;
	}


	
	public void bootApplication(final BundleContext bundleContext, final String contextPath, final String deploy, final String profile) {
		logger.info("====================\nStarting application\n====================\n context: "+contextPath);
		if(instance!=null) {
			logger.error("ALReady running! Ignoring start");
			return;
		}
		this.isRunning = true;
		Thread t = new Thread() {


			@Override
			public void run() {
				try {
					instance = TipiSwingWrapper.runApp(bundleContext,contextPath,deploy,profile);
					instance.getCurrentContext().switchToDefinition(instance.getDefinition());
				} catch (TipiException e) {
					logger.error("Error: ",e);
				}
			}
		};
		t.start();
	}

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}
}
