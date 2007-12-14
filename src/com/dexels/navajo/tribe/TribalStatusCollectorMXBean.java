package com.dexels.navajo.tribe;

public interface TribalStatusCollectorMXBean {

	public int getClusterSize();
	public String getChief();
	public boolean getIsChief();
	public String getMyName();
	public String getClusterMembers();
	public String getClusterCPULoads();
	public String getClusterThreadCounts();
	public String getClusterJoinDates();
}
