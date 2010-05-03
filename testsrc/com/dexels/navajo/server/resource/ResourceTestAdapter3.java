package com.dexels.navajo.server.resource;

public class ResourceTestAdapter3 {

	public ResourceManager getResourceManager(String resourceType) {
		return new TestResourceManager();
	}
	
}
