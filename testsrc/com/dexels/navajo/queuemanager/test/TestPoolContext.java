package com.dexels.navajo.queuemanager.test;

import java.util.HashSet;
import java.util.Set;

import com.dexels.navajo.queuemanager.api.PoolContext;


public class TestPoolContext implements PoolContext {

	private final String POOL1 = "NormalPool";
	private final double POOL1_HEALTH = 0.4;
	private final int POOL1_QUEUE = 3;

	private final String POOL2 = "PriorityPool";
	private final double POOL2_HEALTH = 0.9;
	private final int POOL2_QUEUE = 3;

	@Override
	public double getPoolHealth(String poolName) {
		if(poolName.equals(POOL1)) {
			return POOL1_HEALTH;
		}
		if(poolName.equals(POOL2)) {
			return POOL2_HEALTH;
		}
		throw new IllegalArgumentException("Unknown pool: "+poolName);
	}

	@Override
	public int getQueueSize(String poolName) {
		if(poolName.equals(POOL1)) {
			return POOL1_QUEUE;
		}
		if(poolName.equals(POOL2)) {
			return POOL2_QUEUE;
		}
		throw new IllegalArgumentException("Unknown pool: "+poolName);
	}

	@Override
	public Set<String> getPoolNames() {
		Set<String> pools = new HashSet<String>();
		pools.add(POOL1);
		pools.add(POOL2);
		return pools;
	}

}
