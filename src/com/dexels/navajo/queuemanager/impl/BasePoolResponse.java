package com.dexels.navajo.queuemanager.impl;

import com.dexels.navajo.queuemanager.api.PoolResponse;

public class BasePoolResponse implements PoolResponse {
	private String poolName = null;
	private long cacheTime;
	private long created;
	private String response;

	public BasePoolResponse() {
		created = System.currentTimeMillis();
	}
	
	@Override
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	
	@Override
	public void cacheDecision(long millis) {
		cacheTime = millis;
	}

	@Override
	public void setResponse(String response) {
		this.response = response;
	}

	public String getPoolName() {
		return poolName;
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
