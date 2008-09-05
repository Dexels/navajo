package com.dexels.navajo.server;

import java.util.Date;

public interface DispatcherMXBean {

	/**
	 * Gets the server hostname. 
	 * @return
	 */
	public String getServerId();
	
	/**
	 * Gets the instance_name as specified in the server.xml configuration file.
	 * @return
	 */
	public String getApplicationId();
	
	/**
	 * Gets the total number of currently active Navajo services.
	 * @return
	 */
	public int getAccessSetSize();
	
	/**
	 * Return the peak number of active Navajo services since startup.
	 * @return
	 */
	public int getPeakAccessSetSize();
	
	/**
	 * Resets the peak number of active Navajo services to zero.
	 */
	public void resetAccessSetPeakSize();
	
	/**
	 * Return the time in millis since startup.
	 * @return
	 */
	public long getUptime();
	
	/**
	 * Returns the startup time of this Navajo instance.
	 */
	public Date getStarttime();
	
	/**
	 * Return the current request rate (webservices/second).
	 */
	public float getRequestRate();
	
	/**
	 * Return the total number of requests since startup.
	 * @return
	 */
	public long getRequestCount();
	
	/**
	 * SNMP stuff (not fully operational)
	 * SNMP managers are coded as follows:
     * hostname:port[:V1|V2][,hostname:port[:V1|V2]]*
	 */
	public String getSnmpManangers();
	
	/**
	 * Sets SNMP managers (not fully operational).
	 * @param s
	 */
	public void setSnmpManagers(String s);
	
	/**
	 * Disable Dispatcher for new incoming requests, only handle requests currently being processed.
	 * Requests for asynchronous web services are passed.
	 */
	public void disableDispatcher();
	
	/**
	 * Enable a Dispatcher, i.e. accept new incoming requests.
	 */
	public void enableDispatcher();
	
	/**
	 * Gracefully shutdown a Dispatcher, finishing already running tasks.
	 * 
	 */
	public void shutdown();
	
}
