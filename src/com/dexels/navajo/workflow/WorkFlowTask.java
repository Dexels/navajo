package com.dexels.navajo.workflow;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;

public class WorkFlowTask extends StateStep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -681149898590019046L;
	
	private boolean finished = false;
	
	public WorkFlowTask(State s, Task t) {
		myTask = t;
		myState = s;
	}
	
	public void afterTask(Task t, Navajo request) {
		if ( isMyTaskTrigger(t) ) {
			// Do something that is defined in my nested children tags...
			finished = true;
			// Persist state again, because task has been executed (basically an internal state change!).
			WorkFlowManager.getInstance().persistWorkFlow(myState.getWorkFlow());
		}
	}

	public boolean beforeTask(Task t) {
		return true;
	}

	public boolean isFinished() {
		return finished;
	}

	public String getDescription() {
		return "task:" + myTask.getWebservice();
	}
}
