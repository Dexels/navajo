package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.document.Navajo;

public interface TaskListener {
	
	/**
	 * Return true if task has to be processed, false otherwise.
	 * 
	 * @param t
	 * @return
	 */
	public boolean beforeTask(TaskInterface t, Navajo request);
	public boolean afterTask(TaskInterface t, Navajo response);
	public String getDescription();
	
}
