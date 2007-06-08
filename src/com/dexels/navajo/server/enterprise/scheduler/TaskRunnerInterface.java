package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Map;

public interface TaskRunnerInterface {

	public boolean addTask(TaskInterface t);
	public Map getTasks();
	
}
