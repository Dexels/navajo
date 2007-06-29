package com.dexels.navajo.workflow;

import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;

/**
 * A StateStep defines a step, currently either a transition or a task within a state.
 * StateSteps react to triggers of other tasks. A StateStep needs to be activated and possible cleaned up.
 * 
 * @author arjen
 *
 */
public abstract class StateStep implements TaskListener {

	protected State myState = null;
	protected Task myTask = null;
	
	protected final boolean isMyTaskTrigger(Task t) {
		return ( t.getId().equals(myTask.getId()) &&
				 ( myState == null ||
				   myState.getWorkFlow() == null ||
				     ( myState.getWorkFlow().getDefinition().equals(t.getWorkflowDefinition()) &&
				       myState.getWorkFlow().getMyId().equals(t.getWorkflowId())
				     )
				 )
		 );
	}
	
	/**
	 * Activates a workflowtask, e.g. registers associated task with the taskrunner manager and registers the transition
	 * as a task listener.
	 *
	 */
	public final void activate() {
		TaskRunner.getInstance().addTaskListener(this);
		if ( myTask.getId() == null ) { // It's a first task.
			TaskRunner.getInstance().addTask(myTask);
		} else { // It's a revived task.
			TaskRunner.getInstance().addTask(myTask.getId(), myTask, true);
		}
	}
	
	/**
	 * Deactivates a transition, e.g. deregisters an associated task with the taskrunner manager and deregisters the transition
	 * as a tasklistener.
	 *
	 */
	public final void cleanup() {
		TaskRunner.getInstance().removeTaskListener(this);
		TaskRunner.getInstance().removeTask(myTask.getId());
	}
	

}
