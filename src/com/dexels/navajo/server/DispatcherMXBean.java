package com.dexels.navajo.server;

import java.util.Date;

public interface DispatcherMXBean {

	public String getServerId();
	public String getApplicationId();
	public int getAccessSetSize();
	public int getPeakAccessSetSize();
	public void resetAccessSetPeakSize();
	public long getUptime();
	public Date getStarttime();
	public float getRequestRate();
	public long getRequestCount();
	
	/**
	 * SNMP stuff:
	 * SNMP managers are coded as follows:
     * hostname:port[:V1|V2][,hostname:port[:V1|V2]]*
	 */
	public String getSnmpManangers();
	public void setSnmpManagers(String s);
	
	
}
