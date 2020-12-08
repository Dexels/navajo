/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
