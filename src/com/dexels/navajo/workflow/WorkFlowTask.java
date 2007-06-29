package com.dexels.navajo.workflow;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;

public class WorkFlowTask implements Serializable, TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -681149898590019046L;

	private Task myTask = null;
	private State myState = null;
	private boolean finished = false;
	
	public WorkFlowTask(State s, Task t) {
		myTask = t;
		myState = s;
	}
	
	/**
	 * Activates a workflowtask, e.g. registers associated task with the taskrunner manager and registers the transition
	 * as a task listener.
	 *
	 */
	public void activate() {
		TaskRunner.getInstance().addTaskListener(this);
		if ( myTask.getId() == null ) { // It's a first task.
			TaskRunner.getInstance().addTask(myTask);
		} else { // It's a revived task.
			TaskRunner.getInstance().addTask(myTask.getId(), myTask, true);
		}
	}

	public void cleanup() {
		TaskRunner.getInstance().removeTaskListener(this);
		TaskRunner.getInstance().removeTask(myTask.getId());
	}
	
	private final boolean isMyTaskTrigger(Task t) {
		return ( t.getId().equals(myTask.getId()) &&
				 ( myState == null ||
				   myState.getWorkFlow() == null ||
				     ( myState.getWorkFlow().getDefinition().equals(t.getWorkflowDefinition()) &&
				       myState.getWorkFlow().getMyId().equals(t.getWorkflowId())
				     )
				 )
		 );
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
