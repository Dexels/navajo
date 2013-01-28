package com.dexels.navajo.resource;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceGroup {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ResourceGroup.class);
	private ConfigurationAdmin configAdmin;
	private BundleContext bundleContext;
	private String name;
	private String type;
	
	public void activate(Map<String,Object> settings, BundleContext context) {
		logger.info("Acticating resource group");
//		ManagedServiceFactory
		this.bundleContext = context;
		this.name = (String)settings.get("name");
		this.type = (String)settings.get("type");
		try {
			Filter f = bundleContext.createFilter("(&(service.factoryPid=navajo.resource."+type+")(name="+name+"))");
			ServiceTracker tracker = new ServiceTracker(bundleContext, f,null);
		} catch (InvalidSyntaxException e) {
			logger.error("Problem creating tracker: ",e);
		}
	}

	public void deactivate() {
		
	}
	

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	/**
	 * @param configAdmin the configAdmin to remove 
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}

	
}
