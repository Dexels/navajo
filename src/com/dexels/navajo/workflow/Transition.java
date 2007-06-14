package com.dexels.navajo.workflow;

import java.util.ArrayList;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.scheduler.IllegalTask;
import com.dexels.navajo.scheduler.IllegalTrigger;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.SystemException;

/**
 * A Transition is basically contains a task/trigger, next state value and a condition.
 * Within a transition local workflow parameters can be set.
 * 
 * @author arjen
 *
 */
public class Transition implements TaskListener {

	private Task myTask;
	private State myState;
	private String nextState;
	private String myCondition;
	private final ArrayList parameters = new ArrayList();
	
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
	}
	
	private Transition() {
		
	}
	
	public static Transition createStartTransition(String startStateId, String triggerString, String condition, String activateWorkflow) throws IllegalTrigger, IllegalTask{	
		Transition t = new Transition();
		t.myTask = new Task(null, "ROOT", "", null, triggerString, null);
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
			TaskRunner.getInstance().addTask(myTask.getId(), myTask);
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
		System.err.println("In enterNextState logic");
		if ( myCondition == null || myCondition.equals("") || t.getTrigger().getAccess() == null ) {
			return true;
		}
		Navajo n = t.getTrigger().getAccess().getOutputDoc();
		// Merge this Navajo with localNavajo store of the workflow instance to use local workflow parameters.
		myState.getWorkFlow().mergeWithParmaters(n);
		try {
			n.write(System.err);
		} catch (NavajoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.err.println("About to evaluate next state condition: " + myCondition);
			return Condition.evaluate(myCondition, n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
			return false;
		}
	}
	
	public void afterTask(Task t, Navajo request) {
		
		if ( isMyTransitionTaskTrigger(t) ) {
			System.err.println("In transtition of state " + ( myState != null ? myState.getId() : "") + ",  instance Received afterTask event from task: " + t.getId());

			if ( enterNextState(t) && !activationTranstion ) {
				// First evaluate all parameters that need to be set with this transition.
				evaluateParameters(t);
				myState.leave();
				if ( nextState != null ) {
					System.err.println("About to enter next workflow state: " + nextState);
					myState.getWorkFlow().createState(nextState).enter();
				} else {
					System.err.println("Workflow is finished!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
			}
			/**
			 * If this is a special activationTranstion, do not clean up this transition, simply create new workflow.
			 */
			if ( activationTranstion ) {
				// Activate new workflow instance.
				System.err.println("Initiating new workflow '" + workFlowToBeActivated + "', with start state: " + nextState);
				WorkFlow.getInstance(workFlowToBeActivated, nextState);
			}
		}

	}

	public void beforeTask(Task t) {
		System.err.println("In Workflow instance Received beforeTask event from task: " + t.getId());
	}

	public boolean isActivationTranstion() {
		return activationTranstion;
	}

	public void setActivationTranstion(boolean activationTranstion) {
		this.activationTranstion = activationTranstion;
	}

}
