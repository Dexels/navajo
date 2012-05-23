package com.dexels.navajo.server.resource;

public class ResourceTestAdapter {

	public static ResourceManager getResourceManager(String resourceType) {
		return new TestResourceManager();
	}
}
