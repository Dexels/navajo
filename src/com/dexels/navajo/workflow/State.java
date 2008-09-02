package com.dexels.navajo.workflow;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.dexels.navajo.scheduler.IllegalTask;
import com.dexels.navajo.scheduler.IllegalTrigger;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;

/**
 * A State consists of transitions and tasks.
 * 
 * A task contains: A webservice with an optional trigger and an optional condition.
 * A transition contains: (1) A 'task' without a webservice but only a trigger with an optinal condition.
 *                        (2) A next state to enter when transition was taken.
 * @author arjen
 *
 */

public final class State implements Serializable, Mappable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1658431106536428135L;
	
	public  final String id;
	public  Date entryDate = null;
	public  Date leaveDate = null;
	public  boolean killed = false;
	public  Transition [] transitions = null;
	public  Access initiatingAccess;
	private transient SoftReference<Access> softInitiatingAccess = null;
	public  String initiatingAccessId = null;
	// SHOULD THIS BE A SOFTREFERENCE????
	private final WorkFlow myWorkFlow;
	//private final static ReferenceQueue<WorkFlow> queue = new ReferenceQueue<WorkFlow>();
	
	private final HashSet<Transition> myTransitions = new HashSet<Transition>();
	private final HashSet<WorkFlowTask> myTasks = new HashSet<WorkFlowTask>();
	
	private final String myWorkFlowId;
	
	protected boolean aftertaskentry = false;
	
	protected State(String s, WorkFlow wf, Access a) {
		id = s;
		myWorkFlow = wf;
		myWorkFlowId = wf.getMyId();
		if ( a != null ) {
			softInitiatingAccess = new SoftReference<Access>(a);
		} else {
			// Could be triggered by e.g. time trigger, in which case there is no access object, use initiatingaccess object from workflow (if it exists!).
			if ( getWorkFlow().currentState != null && getWorkFlow().currentState.getInitiatingAccess() != null ) {
				softInitiatingAccess = new SoftReference<Access>( getWorkFlow().currentState.getInitiatingAccess() );
			} else {
				softInitiatingAccess = new SoftReference<Access>(new Access(-1, -1, -1, "", null, null, null, null, false, null));
			}
		}
		persistAccess(a);
	}
	
	public void addTask(String webservice, String trigger, String condition, String navajo) throws IllegalTrigger, IllegalTask  {
		
		WorkFlow wf = getWorkFlow();
		
		if ( trigger == null || trigger.equals("")) {
			trigger = "immediate";
		}
		// Task should be schedule with user that initiated this state.
		Task task = new Task(webservice, getInitiatingAccess().rpcUser, "", null, trigger, null);
		task.setWorkflowDefinition( wf.getDefinition());
		task.setWorkflowId( wf.getMyId() );
		WorkFlowTask wft = new WorkFlowTask(this, task, navajo);
		myTasks.add(wft);
	}
	
	/**
	 * Create a new work flow Transition.
	 * 
	 * @param nextState, name of the next state when transition is performed
	 * @param trigger, the description of the trigger for activating this Transition
	 * @param condition, an optional condition 
	 * @param webservice, an optional webservice to be executed when the transition is made
	 * @return a Transition object
	 * @throws IllegalTrigger
	 * @throws IllegalTask
	 */
	public Transition addTransition(String nextState, String trigger, String condition, String webservice) throws IllegalTrigger, IllegalTask  {
		
		WorkFlow wf = getWorkFlow();
		
		if ( trigger == null || trigger.equals("")) {
			trigger = "immediate";
		}
		
		Task task = new Task(webservice, getInitiatingAccess().rpcUser, "", null, trigger, null);
		task.setWorkflowDefinition( wf.getDefinition() );
		task.setWorkflowId( wf.getMyId() );
		Transition t = new Transition(this, nextState, task, condition, trigger);	
		myTransitions.add(t);
		return t;
	}
	
	/**
	 * Create a new work flow Transition.
	 * 
	 * @param nextState, name of the next state when transition is performed
	 * @param trigger, the description of the trigger for activating this Transition
	 * @param condition, an optional condition 
	 * @return
	 * @throws IllegalTrigger
	 * @throws IllegalTask
	 */
	public Transition addTransition(String nextState, String trigger, String condition) throws IllegalTrigger, IllegalTask  {
		
		WorkFlow wf = getWorkFlow();
		
		if ( trigger == null || trigger.equals("")) {
			trigger = "immediate";
		}
		
		Task task = new Task("", getInitiatingAccess().rpcUser, "", null, trigger, null);
		task.setWorkflowDefinition( wf.getDefinition() );
		task.setWorkflowId( wf.getMyId() );
		Transition t = new Transition(this, nextState, task, condition, trigger);	
		myTransitions.add(t);
		return t;
	}
	
	/**
	 * Method to be called when entering this state.
	 * If b is set to true, it's an after task entry otherwise its a before task entry.
	 *
	 */
	public void enter(boolean b) {
       
		aftertaskentry = b;
		
		// Set entry date.
		entryDate = new Date();
		
        // Activate transitions.
		Iterator<Transition> i = myTransitions.iterator();
		while ( i.hasNext() ) {
			Transition t = i.next();
			t.activate();
		}	
		
	    // Activate tasks.
		Iterator<WorkFlowTask> tks = myTasks.iterator();
		
		while ( tks.hasNext() ) {
			WorkFlowTask wtf = tks.next();
			if (!wtf.isFinished()) { // In case of revival, a workflowtask could already have been executed(!)
				wtf.activate();
			} else {
				System.err.println("Do not activate workflowtask..............it was already finished");
			}
		}
		
		 // Persist workflow instance. This method will be entry point for resurection.
		WorkFlowManager.getInstance().persistWorkFlow(getWorkFlow());
		
	}
	
	/**
	 * Method to be called when this is the initial state of a work flow.
	 */
	public void waitForAction() {
        // Activate transitions.
		Iterator<Transition> i = myTransitions.iterator();
		while ( i.hasNext() ) {
			Transition t = i.next();
			t.activate();
		}
	}
	
	/**
	 * Method to be called when leaving this state.
	 */
	public void leave() {
		// Clean up all tasks/triggers.
		Iterator<WorkFlowTask> i2 = myTasks.iterator();
		while ( i2.hasNext() ) {
			WorkFlowTask t = i2.next();
			t.cleanup();
		}
		Iterator<Transition> i = myTransitions.iterator();
		while ( i.hasNext() ) {
			Transition t = i.next();
			t.cleanup();
		}
		leaveDate = new Date();
		
		// Clear up stuff to allow for garbage collection.
		if ( transitions != null ) {
			for ( int j = 0; j < transitions.length; j++ ) {
				transitions[j].myTask = null;
				transitions[j].myState = null;
			}
		}
		
		getWorkFlow().historicStates.add(this);
	}

	public String getId() {
		return id;
	}
	
	public WorkFlow getWorkFlow() throws RuntimeException {
				
		return myWorkFlow;
	
	}

	public void setKill() {
		leave();
		killed = true;
	}
	
	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	public Transition[] getTransitions() {
		HashSet<Transition> copy = new HashSet<Transition>();
		copy.addAll(myTransitions);
		transitions = new Transition[copy.size()];
		transitions = copy.toArray(transitions);
		
		return transitions.clone();
		
	}

	public Date getEntryDate() {
		if ( entryDate != null ) {
			return (Date) entryDate.clone();
		} else {
			return null;
		}
	}

	public Date getLeaveDate() {
		if ( leaveDate != null ) {
			return (Date) leaveDate.clone();
		} else {
			return null;
		}
	}

	public final boolean getKilled() {
		return killed;
	}
	
	public Access getInitiatingAccess() {
		if ( softInitiatingAccess != null && softInitiatingAccess.get() != null) {
			return softInitiatingAccess.get();
		} else {
			try {
				System.err.println("SOFTREFERENCE CLEANUP, initiatingAccess = " + softInitiatingAccess );
				softInitiatingAccess = new SoftReference( (Access) SharedStoreFactory.getInstance().get(WorkFlowManager.getInstance().workflowPath, initiatingAccessId) );
				//System.err.println("CREATED: " + softInitiatingAccess.get());
				return softInitiatingAccess.get();
			} catch (Throwable e) {
				e.printStackTrace(System.err);
				return null;
			}
		}
	}
	
	private boolean persistAccess(Access a) {

		try {
			initiatingAccessId = getWorkFlow().getMyId() + "-statedump" + getId() + "-" + a.hashCode();
			SharedStoreFactory.getInstance().store(WorkFlowManager.getInstance().workflowPath, 
					initiatingAccessId, a, false, false);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected void removePersistedAccess() {
		if ( initiatingAccessId != null ) {
			SharedStoreFactory.getInstance().remove(WorkFlowManager.getInstance().workflowPath, initiatingAccessId);
		}
	}
	
}
