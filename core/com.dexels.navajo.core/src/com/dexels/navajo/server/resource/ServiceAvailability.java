package com.dexels.navajo.server.resource;

/**
 * Class that can be used to specify the availability of a service based on dependent Resources.
 * 
 * @author arjen
 *
 */
public class ServiceAvailability {

	public final static int STATUS_UNKNOWN = -1;
	public final static int STATUS_OK = 0;
	public final static int STATUS_BUSY = 1;
	public final static int STATUS_VERYBUSY = 2;
	public final static int STATUS_DEAD = 3;
	
	/**
	 * Flag to determine whether this resource if available (true) or temporarily unavailable (false).
	 */
	private boolean available = true;
	/**
	 * Health of the resource: STATUS_OK, STATUS_BUSY, STATUS_VERYBUSY or STATUS_DEAD (implies available = false).
	 */
	private int health = STATUS_OK;
	private String webservice;


	private int MIN_WAITING_TIME = 100;
	private int waitingTime = MIN_WAITING_TIME;
	public String [] unavailableResources = new String[]{};

	public ServiceAvailability(String webservice, boolean available, int health, int waitingTime, String [] unavailableResources) {
		this.webservice = webservice;
		this.available = available;
		this.waitingTime = waitingTime;
		this.health = health;
		this.unavailableResources = unavailableResources;
	}
	
	public String getStatus() {
		switch (health) {
			case STATUS_UNKNOWN:
				return "unknown";
			case STATUS_OK:
				return "ok";
			case STATUS_BUSY:
				return "busy";
			case STATUS_VERYBUSY:
				return "verybusy";
			case STATUS_DEAD:
				return "dead";
	
			default:
				return "unknown";
		}
	}
	
	public int getHealth() {
		return health;
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
		return ( waitingTime > MIN_WAITING_TIME ? waitingTime : MIN_WAITING_TIME );
	}
	
	public String getWebservice() {
		return webservice;
	}
	
	/**
	 * 
	 * @return a list of unavailable resource ids.
	 */
	public String [] getBlockingResourceIds() {
		return unavailableResources;
	}
	
}
