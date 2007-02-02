package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface TaskMXBean {

	public String getWebservice();
	public String getUsername();
	public String getId();
	public boolean isRunning();
	public String getTriggerDescription();
	public boolean isActive();
}
