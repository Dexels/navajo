package com.dexels.navajo.workflow;

import java.io.Serializable;
import java.util.StringTokenizer;

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
			if (  myState.getInitiatingAccess() != null ) {
				myTask.setUsername(myState.getInitiatingAccess().rpcUser);
				myTask.setPassword(myState.getInitiatingAccess().rpcPwd);
			}
			if ( navajoToUse != null && getSourceState().equals(".") && getSourceDirection().equalsIgnoreCase("request") && myState.getInitiatingAccess().getInDoc() != null ) {
				myState.getWorkFlow().mergeWithParameters(myState.getInitiatingAccess().getInDoc());
				myTask.setRequest(myState.getInitiatingAccess().getInDoc());
			} else if ( navajoToUse != null && getSourceState().equals(".") && getSourceDirection().equalsIgnoreCase("response") && myState.getInitiatingAccess().getOutputDoc() != null ) {
				myState.getWorkFlow().mergeWithParameters(myState.getInitiatingAccess().getOutputDoc());
				myTask.setRequest(myState.getInitiatingAccess().getOutputDoc());
			} else if ( navajoToUse != null && !getSourceState().equals(".") ) { // Could be that historic state is referenced.
				String state = getSourceState();
				String reqresponse = getSourceDirection();
				State h = myState.getWorkFlow().getHistoricState(state);
				Navajo alt = null;
				if ( h != null && h.getInitiatingAccess() != null ) {
					if ( reqresponse.equals("response") ) {
						alt = h.getInitiatingAccess().getOutputDoc();
					} else {
						alt = h.getInitiatingAccess().getInDoc();
					}
				}
				myState.getWorkFlow().mergeWithParameters(alt);
				myTask.setRequest(alt);
			} else if ( myState.aftertaskentry &&  myState.getInitiatingAccess().getOutputDoc() != null ) {
				myState.getWorkFlow().mergeWithParameters(myState.getInitiatingAccess().getOutputDoc());
				myTask.setRequest(myState.getInitiatingAccess().getOutputDoc());
			} else if ( myState.getInitiatingAccess().getInDoc() != null ) {
				myState.getWorkFlow().mergeWithParameters(myState.getInitiatingAccess().getInDoc());
				myTask.setRequest(myState.getInitiatingAccess().getInDoc());
			} else { // Maybe time trigger??
				Navajo newDoc = NavajoFactory.getInstance().createNavajo();
				myState.getWorkFlow().mergeWithParameters(newDoc);
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
		TaskRunner.getInstance().removeTask(myTask.getId(), true);
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

	public boolean beforeTask(Task t, Navajo response) {
		return true;
	}

	public boolean isFinished() {
		return finished;
	}

	public String getDescription() {
		return "task:" + myTask.getWebservice();
	}
	
	public boolean hasDefinedSourceNavajo() {
		return ( navajoToUse != null);
	}
	
	public String getSourceState() {
		if ( navajoToUse == null ) {
			return ".";
		}
		StringTokenizer sts = new StringTokenizer(navajoToUse, ":");
		String state = ".";
		if ( sts.hasMoreTokens() ) {
			state =  sts.nextToken();
		}
		if ( state.equals(myState.getId())) {
			return ".";
		} else {
			return state;
		}
	}
	
	public String getSourceDirection() {
		if ( navajoToUse == null ) {
			return null;
		}
		StringTokenizer sts = new StringTokenizer(navajoToUse, ":");
		if ( sts.hasMoreTokens() ) {
			sts.nextToken();
			if ( sts.hasMoreTokens() ) {
				return sts.nextToken();
			}
		}
		return "request";
	}
}
