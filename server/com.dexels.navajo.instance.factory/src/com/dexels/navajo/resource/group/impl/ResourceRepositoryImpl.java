package com.dexels.navajo.resource.group.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.resource.group.ResourceGroup;
import com.dexels.navajo.resource.group.ResourceRepository;

public class ResourceRepositoryImpl implements ResourceRepository {

	private static ResourceRepository instance = null;
	private final Map<String,ResourceGroup> resourceGroups = new HashMap<String, ResourceGroup>();
	
	
	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}

	public static ResourceRepository getInstance() {
		return instance;
	}
	
	public void addResourceGroup(ResourceGroup group) {
		resourceGroups.put(group.getName(), group);
	}
	
	public void removeResourceGroup(ResourceGroup group) {
		resourceGroups.remove(group.getName());
	}

	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.resource.group.impl.ResourceRepository#getResourceGroup(java.lang.String)
	 */
	@Override
	public ResourceGroup getResourceGroup(String resourceName) {
		return resourceGroups.get(resourceName);
	}

}
