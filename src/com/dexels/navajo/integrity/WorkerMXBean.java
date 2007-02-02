package com.dexels.navajo.integrity;

import com.dexels.navajo.server.jmx.GenericThreadMXBean;

public interface WorkerMXBean extends GenericThreadMXBean {

	public int getCacheSize();
	public int getWorkSize();
	public int getNotWrittenSize();
	public int getFileCount();

}
