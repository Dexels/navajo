package com.dexels.navajo.adapter.queue;

public interface RequestResponseQueueMXBean {

	public int getSize();
	public void emptyQueue();
	public void setMaxThreads(int t);
	public int getMaxThreads();
	public void setSleepingTime(long l);
	public long getSleepingTime();
	
}
