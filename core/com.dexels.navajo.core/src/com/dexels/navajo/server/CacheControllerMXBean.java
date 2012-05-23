package com.dexels.navajo.server;

public interface CacheControllerMXBean {

	public int cachedEntries();
	public double getHitRate();
	public void clearCache();
	
}
