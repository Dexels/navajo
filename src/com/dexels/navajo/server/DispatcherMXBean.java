package com.dexels.navajo.server;

public interface DispatcherMXBean {

	public String getServerId();
	public String getApplicationId();
	public int getAccessSetSize();
	public int getPeakAccessSetSize();
	public void resetAccessSetPeakSize();
	
}
