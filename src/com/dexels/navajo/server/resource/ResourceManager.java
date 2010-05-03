package com.dexels.navajo.server.resource;

public interface ResourceManager {

	public boolean isAvailable(String resourceId);
	public int getWaitingTime(String resourceId);
}
