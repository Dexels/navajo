package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;

public interface TaskListener {
	
	/**
	 * Return true if task has to be processed, false otherwise.
	 * 
	 * @param t
	 * @return
	 */
	public boolean beforeTask(Task t);
	public void afterTask(Task t, Navajo request);
	
}
