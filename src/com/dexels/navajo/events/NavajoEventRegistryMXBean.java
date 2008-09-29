package com.dexels.navajo.events;

public interface NavajoEventRegistryMXBean {

	/*
	 * JMX methods.
	 */
	public void addMonitoredEvent(String type, String level);
	public void addMonitoredEvent(String type);
	public void removeMonitoredEvent(String type);
	public String getMonitoredEvents();
	
	/*
	 * Event registry methods.
	 */
	public int getNumberOfRegisteredListeners();
	
}
