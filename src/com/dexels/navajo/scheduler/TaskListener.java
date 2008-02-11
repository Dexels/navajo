package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;

public interface TaskListener {
	
	/**
	 * Return true if task has to be processed, false otherwise.
	 * 
	 * @param t
	 * @return
	 */
	public boolean beforeTask(Task t, Navajo request);
	public void afterTask(Task t, Navajo response);
	public String getDescription();
	
}
