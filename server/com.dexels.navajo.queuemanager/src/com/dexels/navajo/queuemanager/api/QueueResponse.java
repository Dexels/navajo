package com.dexels.navajo.queuemanager.api;

public interface QueueResponse {
	
	public static final String ACCEPT = "accept";
	public static final String REFUSE = "refuse";
	
	public void setResponse(String status);
	public void setQueueName(String queueName);
	public void cacheDecision(long millis);
}
