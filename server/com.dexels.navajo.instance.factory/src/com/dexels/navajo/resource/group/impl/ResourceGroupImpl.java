package com.dexels.navajo.resource.group.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.group.ResourceGroup;

public class ResourceGroupImpl implements ResourceGroup {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ResourceGroupImpl.class);
	private ConfigurationAdmin configAdmin;
	private BundleContext bundleContext;
	private String name;
	private String type;
	private ServiceTracker tracker;
	
	private final Map<String,ServiceReference> references = new HashMap<String,ServiceReference>();
	private final Map<String,Object> serviceObjects = new HashMap<String,Object>();
	
	@SuppressWarnings("unchecked")
	public void activate(Map<String,Object> settings, BundleContext context) {
		logger.info("Acticating resource group");
//		ManagedServiceFactory
		this.bundleContext = context;
		this.name = (String)settings.get("name");
		this.type = (String)settings.get("type");
		try {
			Filter f = bundleContext.createFilter("(&(service.factoryPid=navajo.resource."+type+")(name=navajo.resource."+name+"))");
			tracker = new ServiceTracker(bundleContext, f,null) {

				@Override
				public Object addingService(ServiceReference reference) {
					final String instance = (String) reference.getProperty("instance");
					logger.info("Service added: "+reference.getProperty("name")+" type: "+reference.getProperty("type")+" instance: "+instance);
					references.put(instance, reference);
					Object service = context.getService(reference);
					serviceObjects.put(instance, service);
					return super.addingService(reference);
				}

				@Override
				public void modifiedService(ServiceReference reference,
						Object service) {
					logger.info("Service modified: "+reference.getProperty("name")+" type: "+reference.getProperty("type"));
					super.modifiedService(reference, service);
				}

				@Override
				public void removedService(ServiceReference reference,
						Object service) {
					final String instance = (String) reference.getProperty("instance");
					logger.info("Service removed: "+reference.getProperty("name")+" type: "+reference.getProperty("type"));
					references.remove(instance);
					super.removedService(reference, service);
				}
				
			};
			tracker.open();
		} catch (InvalidSyntaxException e) {
			logger.error("Problem creating tracker: ",e);
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Object getResource(String instance) {
		return serviceObjects.get(instance);
	}
	
	public void deactivate() {
		tracker.close();
		tracker = null;
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
