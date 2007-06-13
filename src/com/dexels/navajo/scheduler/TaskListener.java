package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;

public interface TaskListener {
	
	public void beforeTask(Task t);
	public void afterTask(Task t, Navajo request);
	
}
