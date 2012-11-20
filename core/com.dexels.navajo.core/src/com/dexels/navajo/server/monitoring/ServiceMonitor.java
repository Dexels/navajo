package com.dexels.navajo.server.monitoring;

import com.dexels.navajo.server.Access;

public interface ServiceMonitor {

	/*
	 * Determine if access object needs full access log.
	 *
	 * @param a the full access log candidate
	 * @return whether full access log is required for access object.
	 */
	public abstract boolean needsFullAccessLog(Access a);

	/**
	 * Check if full access log monitor is enabled.
	 *
	 * @return
	 */
	public abstract boolean isMonitorOn();

	/**
	 * Set enabled/disable full access log monitor.
	 *
	 * @param monitorOn
	 */
	public abstract void setMonitorOn(boolean monitorOn);

	/*
	 * Get r.e. for user monitor filter. If null is returned all users should be logged.
	 *
	 * @return the current filter
	 */
	public abstract String getMonitorUsers();

	/*
	 * Set r.e. for user monitor filter. Null or empty string means no filter.
	 *
	 * @param monitorUsers
	 */
	public abstract void setMonitorUsers(String monitorUsers);

	public abstract void setMonitorWebservices(String monitorWebservices);

	/*
	 * Get r.e. for webservice monitor filter. If null is returned all users should be logged.
	 *
	 * @return the current filter
	 */
	public abstract String getMonitorWebservices();

	/*
	 * Get the time in millis over which an access needs to be fully logged.
	 *
	 * @return
	 */
	public abstract int getMonitorExceedTotaltime();

	/**
	 * Set the time in millis over which an access needs to be fully logged.
	 *
	 * @param monitorExceedTotaltime
	 */
	public abstract void setMonitorExceedTotaltime(int monitorExceedTotaltime);
	


}