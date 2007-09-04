package com.dexels.navajo.workflow;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
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
	private String navajoToUse = null;
	private boolean finished = false;
	
	public WorkFlowTask(State s, Task t, String navajoToUse) {
		myTask = t;
		myState = s;
		this.navajoToUse = navajoToUse;
	}
	
	/**
	 * Activates a workflowtask, e.g. registers associated task with the taskrunner manager and registers the transition
	 * as a task listener.
	 *
	 */
	public void activate() {
		
		TaskRunner.getInstance().addTaskListener(this);
		if ( myState != null ) {
			// Set request Navajo based upon initiating access webservice of my state.
			if (  myState.initiatingAccess != null ) {
				myTask.setUsername(myState.initiatingAccess.rpcUser);
				myTask.setPassword(myState.initiatingAccess.rpcPwd);
			}
			if ( navajoToUse != null && navajoToUse.equalsIgnoreCase("request") && myState.initiatingAccess.getInDoc() != null ) {
				myState.getWorkFlow().mergeWithParmaters(myState.initiatingAccess.getInDoc());
				myTask.setRequest(myState.initiatingAccess.getInDoc());
			} else if ( navajoToUse != null && navajoToUse.equalsIgnoreCase("response") && myState.initiatingAccess.getInDoc() != null ) {
				myState.getWorkFlow().mergeWithParmaters(myState.initiatingAccess.getOutputDoc());
				myTask.setRequest(myState.initiatingAccess.getOutputDoc());
			} else if ( myState.aftertaskentry &&  myState.initiatingAccess.getOutputDoc() != null ) {
				myState.getWorkFlow().mergeWithParmaters(myState.initiatingAccess.getOutputDoc());
				myTask.setRequest(myState.initiatingAccess.getOutputDoc());
			} else if ( myState.initiatingAccess.getInDoc() != null ) {
				myState.getWorkFlow().mergeWithParmaters(myState.initiatingAccess.getInDoc());
				myTask.setRequest(myState.initiatingAccess.getInDoc());
			} else { // Maybe time trigger??
				Navajo newDoc = NavajoFactory.getInstance().createNavajo();
				myState.getWorkFlow().mergeWithParmaters(newDoc);
				myTask.setRequest(newDoc);
			}
		}
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
