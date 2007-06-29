package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface ClockMXBean extends GenericThreadMXBean {

	public int getResolution();
	public int getListeners();
	public String getId();
	
}
