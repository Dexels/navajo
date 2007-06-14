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
		if ( myTask.getId() == null ) {
			TaskRunner.getInstance().addTask(myTask);
		} else {
			TaskRunner.getInstance().addTask(myTask.getId(), myTask);
		}
	}

	public void cleanup() {
		TaskRunner.getInstance().removeTaskListener(this);
		TaskRunner.getInstance().removeTask(myTask.getId());
	}
	
	private final boolean isMyTaskTrigger(Task t) {
		System.err.println("myTask = " + myTask);
		System.err.println("t = " + t);
		System.err.println("myState = " + myState);
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
		}
	}

	public void beforeTask(Task t) {
		
	}
}
