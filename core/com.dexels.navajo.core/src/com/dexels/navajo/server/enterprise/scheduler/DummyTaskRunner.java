package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Map;

public class DummyTaskRunner implements TaskRunnerInterface {

	public boolean addTask(TaskInterface t) {
		System.err.println("WARNING: Trying to schedule task. This is not supported in the standard version of Navajo");
		return false;
	}

	public Map getTasks() {
		return null;
	}

	public void removeTask(TaskInterface t) {
		
	}

}
