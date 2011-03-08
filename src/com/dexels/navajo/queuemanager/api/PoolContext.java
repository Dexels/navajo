package com.dexels.navajo.queuemanager.api;

import java.util.Set;

public interface PoolContext {

	public double getPoolHealth(String poolName);
	public int getQueueSize(String poolName);
	public Set<String> getPoolNames();

}