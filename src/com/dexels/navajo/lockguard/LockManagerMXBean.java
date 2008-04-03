package com.dexels.navajo.lockguard;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface LockManagerMXBean extends GenericThreadMXBean {

	public int getLockCount();
	public void clearAllLocks();
	
}
