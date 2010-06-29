package com.dexels.navajo.server.enterprise.scheduler;

public interface TaskMXBean {

	public String getWebservice();
	public String getUsername();
	public String getId();
	public boolean isRunning();
	public String getTriggerDescription();
	public boolean isActive();
}
