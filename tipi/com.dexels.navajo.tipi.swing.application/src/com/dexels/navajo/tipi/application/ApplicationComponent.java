package com.dexels.navajo.tipi.application;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;
import tipi.TipiCoreExtension;
import tipi.TipiExtension;
import tipi.TipiMainExtension;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingWrapper;

@SuppressWarnings({"rawtypes","unused"})

public class ApplicationComponent {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationComponent.class);
	private TipiApplicationInstance instance;

	private ComponentContext componentContext;


	private boolean isRunning = false;
	private boolean isActive = false;
	private Dictionary properties;
	private ConfigurationAdmin configAdmin;
	
	public void activate(ComponentContext c) {
		this.componentContext = c;
		logger.info("Tipi Application Active");
		properties = c.getProperties();

		if(instance!=null) {
			instance.close();
		}
		isActive = true;
		String contextPath = (String) c.getProperties().get("tipi.context");
		String deployment = (String) c.getProperties().get("deployment");
		String profile = (String) c.getProperties().get("profile");

		try {
			Configuration boot = configAdmin.getConfiguration("tipi.boot");
			if(boot.getProperties()==null) {
				logger.warn("No boot config, delaying actvation");
				String tipiBoot = System.getProperty("tipi.boot");
				if(tipiBoot!=null) {
					logger.info("Found a system var, booting after all.");
					if(contextPath!=null) {
						boot.delete();
						bootApplication(componentContext.getBundleContext(),contextPath,deployment,profile);
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
			bootApplication(componentContext.getBundleContext(),contextPath,deployment,profile);
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
