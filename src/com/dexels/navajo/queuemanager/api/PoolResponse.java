package com.dexels.navajo.queuemanager.api;

public interface PoolResponse {
	
	public static final String ACCEPT = "accept";
	public static final String REFUSE = "refuse";
	
	public void setResponse(String status);
	public void setPoolName(String poolName);
	public void cacheDecision(long millis);
}
