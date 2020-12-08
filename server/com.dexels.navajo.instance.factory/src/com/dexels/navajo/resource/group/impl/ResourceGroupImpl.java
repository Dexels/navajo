/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.group.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.group.ResourceGroup;

public class ResourceGroupImpl implements ResourceGroup {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ResourceGroupImpl.class);
	private BundleContext bundleContext;
	private String name;
	private String type;
	private ServiceTracker<Object,Object> tracker;
	
	private final Map<String,ServiceReference<Object>> references = new HashMap<String,ServiceReference<Object>>();
	private final Map<String,Object> serviceObjects = new HashMap<String,Object>();
	
	public void activate(Map<String,Object> settings, BundleContext context) {
		logger.info("Acticating resource group");
//		ManagedServiceFactory
		this.bundleContext = context;
		this.name = (String)settings.get("name");
		this.type = (String)settings.get("type");
		try {
			Filter f = bundleContext.createFilter("(&(service.factoryPid=navajo.resource."+type+")(name=navajo.resource."+name+"))");
			tracker = new ServiceTracker<Object, Object>(bundleContext, f,null) {

				@Override
				public Object addingService(ServiceReference<Object> reference) {
					final String instance = (String) reference.getProperty("instance");
					logger.info("Service added: "+reference.getProperty("name")+" type: "+reference.getProperty("type")+" instance: "+instance);
					references.put(instance, reference);
					Object service = context.getService(reference);
					serviceObjects.put(instance, service);
					return super.addingService(reference);
				}

				@Override
				public void modifiedService(ServiceReference<Object> reference,
						Object service) {
					logger.info("Service modified: "+reference.getProperty("name")+" type: "+reference.getProperty("type"));
					super.modifiedService(reference, service);
				}

				@Override
				public void removedService(ServiceReference<Object> reference,
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public Object getResource(String instance) {
		return serviceObjects.get(instance);
	}
	
	public void deactivate() {
		tracker.close();
		tracker = null;
	}
	

	
}
