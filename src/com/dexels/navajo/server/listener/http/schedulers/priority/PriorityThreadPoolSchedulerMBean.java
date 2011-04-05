package com.dexels.navajo.server.listener.http.schedulers.priority;

public interface PriorityThreadPoolSchedulerMBean {

	public void setNormalPoolSize(int pool);
	public void setPriorityPoolSize(int pool);
	public void setSystemPoolSize(int pool);
	public void setSlowPoolSize(int pool);
	public void setFastPoolSize(int pool);
	
	public int getNormalPoolSize();
	public int getPriorityPoolSize();
	public int getSystemPoolSize();
	public int getSlowPoolSize();
	public int getFastPoolSize();
	
	public void setTimeout(int timeout);
	public int getTimeout();

	public int getMaxBackLog();
	public void setMaxBackLog(int backlog);
	
	public double getExpectedNormalQueueTime();
	public double getExpectedPriorityQueueTime();
	public double getExpectedSystemQueueTime();
	
	public int getSystemActive();
	public int getNormalActive();
	public int getPriorityActive();
	public int getSlowActive();
	public int getFastActive();
	
	public int getSystemQueueSize();
	public int getNormalQueueSize();
	public int getPriorityQueueSize();
	public int getSlowQueueSize();
	public int getFastQueueSize();
	
	public int flushSystemQueue();
	public int flushNormalQueue();
	public int flushPriorityQueue();
	public int flushSlowQueue();
	public int flushFastQueue();
	
	public long getProcessed();
	public long getErrors();
	public long getResubmits();

	public void setThrottleDelay(int throttleDelay);
	public int getThrottleDelay();
	
	public int getInterArrivalTime();
	public int getInterDepartureTime();
	
	public long   getArrivals();
	public long   getDepartures();
	
}
