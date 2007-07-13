package com.dexels.navajo.adapter.queue;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface RequestResponseQueueMXBean extends GenericThreadMXBean {

	public int getSize();
	public int getDeadQueueSize();
	public void emptyQueue();
	public void setMaxThreads(int t);
	public int getMaxThreads();
	public void setSleepingTime(long l);
	public long getSleepingTime();
	
}
