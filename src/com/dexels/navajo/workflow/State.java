package com.dexels.navajo.workflow;

import java.util.HashSet;
import java.util.Iterator;

import com.dexels.navajo.scheduler.IllegalTask;
import com.dexels.navajo.scheduler.IllegalTrigger;
import com.dexels.navajo.scheduler.Task;

/**
 * A State consists of transitions and tasks.
 * 
 * A task contains: A webservice with an optional trigger and an optional condition.
 * A transition contains: (1) A 'task' without a webservice but only a trigger with an optinal condition.
 *                        (2) A next state to enter when transition was taken.
 * @author arjen
 *
 */
public class State {
	
	private String id;
	private final HashSet myTransitions = new HashSet();
	private final HashSet myTasks = new HashSet();
	private final WorkFlow myWorkFlow;
	
	public State(String s, WorkFlow wf) {
		id = s;
		myWorkFlow = wf;
	}
	
	public void addTask(String service, String trigger, String condition) {
		
	}
	
	public Transition addTransition(String nextState, String trigger, String condition) throws IllegalTrigger, IllegalTask  {
		Task task = new Task("", "ROOT", "", null, trigger, null);
		task.setWorkflowDefinition(myWorkFlow.getDefinition());
		task.setWorkflowId(myWorkFlow.getMyId());
		Transition t = new Transition(this, nextState, task, condition);	
		myTransitions.add(t);
		return t;
	}
	
	/**
	 * Method to be called when entering this state.
	 *
	 */
	public void enter() {
		
		// Activate transitions.
		Iterator i = myTransitions.iterator();
		while ( i.hasNext() ) {
			Transition t = (Transition) i.next();
			t.activate();
		}
	}
	
	/**
	 * Method to be called when this is the initial state of a workflow.
	 *
	 */
	public void waitForAction() {
        // Activate transitions.
		Iterator i = myTransitions.iterator();
		while ( i.hasNext() ) {
			Transition t = (Transition) i.next();
			t.activate();
		}
	}
	
	/**
	 * Method to be called when leaving this state.
	 *
	 */
	public void leave() {
		// Clean up all tasks/triggers.
		Iterator i = myTransitions.iterator();
		while ( i.hasNext() ) {
			Transition t = (Transition) i.next();
			t.cleanup();
		}
		myTransitions.clear();
		myWorkFlow.removeState(this);
	}

	public String getId() {
		return id;
	}
	
	public WorkFlow getWorkFlow() {
		return myWorkFlow;
	}

}
