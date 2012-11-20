package com.dexels.navajo.server.resource;

public class ResourceTestAdapter {

	/**
	 * @param resourceType  
	 */
	public static ResourceManager getResourceManager(String resourceType) {
		return new TestResourceManager();
	}
}
