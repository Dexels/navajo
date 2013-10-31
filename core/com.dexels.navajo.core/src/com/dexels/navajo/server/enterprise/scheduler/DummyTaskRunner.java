package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyTaskRunner implements TaskRunnerInterface {

		
		private final static Logger logger = LoggerFactory
				.getLogger(DummyTaskRunner.class);
		
	
	@Override
	public boolean addTask(TaskInterface t) {
		logger.warn("WARNING: Trying to schedule task. This is not supported in the standard version of Navajo");
		return false;
	}

	@Override
	public Map getTasks() {
		return null;
	}

	@Override
	public void removeTask(TaskInterface t) {
		
	}

}
