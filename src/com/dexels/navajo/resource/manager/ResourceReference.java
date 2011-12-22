package com.dexels.navajo.resource.manager;

import java.util.Map;

import com.dexels.navajo.resource.ResourceConfig;
import com.dexels.navajo.resource.ResourceInstance;

public class ResourceReference {
	private String name;
	private String resourceConfigName;
	private Map<String, Object> settings;
	private ResourceInstance instance;

	public ResourceReference(String name, String resourceConfigName, Map<String, Object> settings) {
		this.name = name;
		this.settings = settings;
		this.resourceConfigName = resourceConfigName;
	}
	
	public String getResourceConfigName() {
		return resourceConfigName;
	}
	public void setResourceConfigName(String resourceConfigName) {
		this.resourceConfigName = resourceConfigName;
	}

	
	public String getName() {
		return name;
	}
	
	public void instantiate(ResourceConfig rc) throws Exception {
		instance = rc.createInstance(settings);
	}

	public void uninstantiate() {
		instance.close();
		instance = null;
	}
	
	public boolean isActive() {
		return instance!=null;
	}

	public ResourceInstance getInstance() {
		return instance;
	}
}
