package com.dexels.navajo.workflow;

import java.io.Serializable;
import java.util.ArrayList;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.scheduler.IllegalTask;
import com.dexels.navajo.scheduler.IllegalTrigger;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;

/**
 * A Transition is basically contains a task/trigger, next state value and a condition.
 * Within a transition local workflow parameters can be set.
 * 
 * @author arjen
 *
 */
public class Transition implements TaskListener, Serializable, Mappable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144803468988103221L;
	
	public String nextState;
	public String myCondition;
	public String trigger;
	public String webservice = null;
	public boolean proxy = false;
	
	private Task myTask = null;
	private State myState;
	private boolean beforeTrigger = false;
	private final ArrayList parameters = new ArrayList();
	private static Object semaphore = new Object();
	
	/**
	 * An transition is an activationTranstion if it can activate a new workflow instance.
	 * It's basically a transition that waits for a certain event to happen and to create a new workflow instance.
	 */
	private boolean activationTranstion = false;
	private String workFlowToBeActivated = null;
	
	public Transition(State s, String nextStateId, Task t, String condition) {
		myTask = t;
		myState = s;
		nextState = nextStateId;
		myCondition = condition;
		if ( t.getTriggerDescription().startsWith("beforenavajo")) {
			beforeTrigger = true;
		}
		trigger = t.getTriggerDescription();
	}
	
	private Transition() {
		
	}
	
	public static Transition createStartTransition(String startStateId, String triggerString, String condition, String activateWorkflow) throws IllegalTrigger, IllegalTask{	
		Transition t = new Transition();
		t.myTask = new Task(null, "ROOT", "", null, triggerString, null);
		t.trigger = triggerString;
		t.myTask.setId("workflow-"+activateWorkflow);
		t.myTask.setWorkflowDefinition(activateWorkflow);
		t.nextState = startStateId;
		t.myCondition = condition;
		t.activationTranstion = true;
		t.workFlowToBeActivated = activateWorkflow;
		t.activate();
		return t;
	}
	
	public void addParameter(String name, String expression) {
		Parameter p = new Parameter(name, expression);
		parameters.add(p);
	}
	
	/**
	 * Evaluate all parameters.
	 *
	 */
	private void evaluateParameters(Task t) {
		if ( t.getTrigger().getAccess() != null ) {
			Navajo n = t.getTrigger().getAccess().getOutputDoc();
			for ( int i = 0; i < parameters.size(); i++ ) {
				Parameter p = (Parameter) parameters.get(i);
				String name = p.getName();
				String expression = p.getExpression();
				try {
					Operand o = Expression.evaluate(expression, n);
					myState.getWorkFlow().addParameter(name, o.value);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				} 
			}
		}
	}
	
	private final boolean isMyTransitionTaskTrigger(Task t) {
		
		synchronized ( semaphore ) {
			boolean result= ( t.getId().equals(myTask.getId()) &&
					( myState == null ||
							myState.getWorkFlow() == null ||
							( myState.getWorkFlow().getDefinition().equals(t.getWorkflowDefinition()) &&
									myState.getWorkFlow().getMyId().equals(t.getWorkflowId())
							)
					)
			);
			
			return result;
		}
	}
	
	/**
	 * Activates a transition, e.g. registers associated task with the taskrunner manager and registers the transition
	 * as a task listener.
	 *
	 */
	public void activate() {
		
		TaskRunner.getInstance().addTaskListener(this);
		if ( myTask.getId() == null ) {
			TaskRunner.getInstance().addTask(myTask);
		} else {
			TaskRunner.getInstance().addTask(myTask.getId(), myTask, true);
		}
		
	}
	
	/**
	 * Deactivates a transition, e.g. deregisters an associated task with the taskrunner manager and deregisters the transition
	 * as a tasklistener.
	 *
	 */
	public void cleanup() {
		TaskRunner.getInstance().removeTaskListener(this);
		TaskRunner.getInstance().removeTask(myTask.getId());
	}
	
	private boolean enterNextState(Task t) {
		
		if ( myCondition == null || myCondition.equals("") || t.getTrigger().getAccess() == null ) {
			return true;
		}
		Navajo n = ( beforeTrigger ? t.getTrigger().getAccess().getInDoc() : t.getTrigger().getAccess().getOutputDoc() );
		// Merge this Navajo with localNavajo store of the workflow instance to use local workflow parameters.
		myState.getWorkFlow().mergeWithParmaters(n);
	
		try {
			
			return Condition.evaluate(myCondition, n);
		} catch (Exception e) {
			// Could no evaluate condition, hence must return false.
			//e.printStackTrace(System.err);
			return false;
		}
	}
	
	public void afterTask(Task t, Navajo request) {
		
		if (  !beforeTrigger && isMyTransitionTaskTrigger(t) ) {
			
			if ( enterNextState(t) && !activationTranstion ) {
				// First evaluate all parameters that need to be set with this transition.
				evaluateParameters(t);
				myState.leave();
				if ( nextState != null && !nextState.equals("finish") ) {
					myState.getWorkFlow().createState(nextState).enter();
				} else {
					myState.getWorkFlow().finish();
				}
			}
			/**
			 * If this is a special activationTranstion, do not clean up this transition, simply create new workflow.
			 */
			if ( activationTranstion ) {
				// Activate new workflow instance.
				WorkFlow wf = WorkFlow.getInstance(workFlowToBeActivated, nextState, t.getTrigger().getAccess());
			}
		}

	}

	public boolean beforeTask(Task t) {
		
		if ( beforeTrigger && isMyTransitionTaskTrigger(t) ) {
			
			
			if ( enterNextState(t) ) {
//				 First evaluate all parameters that need to be set with this transition.
				evaluateParameters(t);
				myState.leave();
				if ( nextState != null && !nextState.equals("finish") ) {
					myState.getWorkFlow().createState(nextState).enter();
				} else {
					myState.getWorkFlow().finish();
				}
				return true;
			} else { // Although it was my task, condition did not evaluate to true, return false in order to prevent proxy webservice from running.
				return false;
			}
		}
		
		return true;
	}

	public boolean isActivationTranstion() {
		return activationTranstion;
	}

	public void setActivationTranstion(boolean activationTranstion) {
		this.activationTranstion = activationTranstion;
	}

	public boolean isBeforeTrigger() {
		return beforeTrigger;
	}

	public Task getMyTask() {
		return myTask;
	}

	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public String getTrigger() {
		return trigger;
	}

	public String getMyCondition() {
		return myCondition;
	}

	public String getNextState() {
		return nextState;
	}

	public String getWebservice() {
		if ( myTask != null ) {
			return myTask.webservice;
		}
		return null;
	}

	public boolean getProxy() {
		if ( myTask != null ) {
			return myTask.proxy;
		}
		return false;
	}

	public String getDescription() {
		return "transition:" + trigger + "(" + myCondition + ")->" + nextState;
	}

}
