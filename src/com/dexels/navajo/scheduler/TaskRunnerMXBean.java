package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface TaskRunnerMXBean extends GenericThreadMXBean {

	public int getTaskListSize();
	
}
