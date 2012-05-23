package com.dexels.navajo.queuemanager.impl;

import com.dexels.navajo.queuemanager.api.QueueResponse;

public class BaseQueueResponse implements QueueResponse {
	private String queueName = null;
	private long cacheTime;
	private long created;
	private String response;

	public BaseQueueResponse() {
		created = System.currentTimeMillis();
	}
	
	@Override
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	@Override
	public void cacheDecision(long millis) {
		cacheTime = millis;
	}

	@Override
	public void setResponse(String response) {
		this.response = response;
	}

	public String getQueueName() {
		return queueName;
	}

	public long getCacheTime() {
		return cacheTime;
	}

	public String getResponse() {
		return response;
	}
	
	public boolean isValid() {
		long now = System.currentTimeMillis();
		return (now < created+cacheTime);
	}

}
