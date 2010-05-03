package com.dexels.navajo.server.resource;

/**
 * Class that can be used to specify the availability of a service based on dependent Resources.
 * 
 * @author arjen
 *
 */
public class ServiceAvailability {

	private boolean available = true;
	private int waitingTime = 0;
	public String [] unavailableResources = new String[]{};
	
	public ServiceAvailability(boolean available, int waitingTime, String [] unavailableResources) {
		this.available = available;
		this.waitingTime = waitingTime;
		this.unavailableResources = unavailableResources;
	}
	
	/**
	 * 
	 * @return true if the service is available
	 */
	public boolean isAvailable() {
		return available;
	}
	
	/**
	 * 
	 * @return the minimum waiting time in millis for retries.
	 */
	public int getWaitingTime() {
		return waitingTime;
	}
	
	/**
	 * 
	 * @return a list of unavailable resource ids.
	 */
	public String [] getBlockingResourceIds() {
		return unavailableResources;
	}
	
}
