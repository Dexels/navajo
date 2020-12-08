/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.scheduler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyTaskRunner implements TaskRunnerInterface {

		
		private static final Logger logger = LoggerFactory
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

	@Override
	public TaskInterface createTask() {
		return new DummyTask();
	}

}
