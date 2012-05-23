package com.dexels.navajo.server.resource;

public class ResourceTestAdapter3 {

	/**
	 * NOTE: forgot static method....
	 * 
	 * @param resourceType
	 * @return
	 */
	public ResourceManager getResourceManager(String resourceType) {
		return new TestResourceManager();
	}

}
