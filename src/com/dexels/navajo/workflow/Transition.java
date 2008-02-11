package com.dexels.navajo.workflow;

import java.io.Serializable;
import java.util.ArrayList;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.scheduler.IllegalTask;
import com.dexels.navajo.scheduler.IllegalTrigger;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.scheduler.TaskListener;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;

/**
 * A Transition is basically contains a task/trigger, next state value and a condition.
 * Within a transition local workflow parameters can be set.
 * 
 * @author arjen
 *
 */
public final class Transition implements TaskListener, Serializable, Mappable {

	/**
	 * TODO: LOG WORKFLOW ERRORS SOMEWHERE
	 */
	private static final long serialVersionUID = 3144803468988103221L;
	
	
	public String nextState;
	public String myCondition;
	public String trigger;
	public String webservice = null;
	public boolean proxy = false;
	
	/**
	 * PUBLIC CONSTANTS
	 */
	
	public static final String FINISH = "finish";
	
	private Task myTask = null;
	private State myState;
	private final ArrayList<Parameter> parameters = new ArrayList<Parameter>();
	private static Object semaphore = new Object();
	
	/**
	 * An transition is an activationTranstion if it can activate a new workflow instance.
	 * It's basically a transition that waits for a certain event to happen and to create a new workflow instance.
	 */
	private boolean activationTranstion = false;
	private String workFlowToBeActivated = null;
	
	public Transition(State s, String nextStateId, Task t, String condition, String origTriggerDescription) {
		myTask = t;
		myState = s;
		nextState = nextStateId;
		myCondition = condition;
		trigger = origTriggerDescription;
	}
	
	private Transition() {
		
	}
	
	public final static Transition createStartTransition(String startStateId, String triggerString, String condition, String activateWorkflow, String username) throws IllegalTrigger, IllegalTask{	
		Transition t = new Transition();
		t.myTask = new Task(null, username, "", null, triggerString, null);
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
	
	public final void addParameter(String name, ArrayList<ConditionalExpression> expressions) {
		Parameter p = new Parameter(name, expressions);
		parameters.add(p);
	}
	
	/**
	 * Evaluate all parameters.
	 *
	 */
	private final void evaluateParameters(Task t, State usethisState)  {

		Navajo n = null;
		if ( t.getTrigger().getAccess() != null ) {
			n = t.getTrigger().getAccess().getOutputDoc();
			// If outputdoc does not exist, it must be beforenavajo transition trigger, use indoc instead.
			if ( n == null ) {
				n = t.getTrigger().getAccess().getInDoc();
			} 
		} else {
			n = NavajoFactory.getInstance().createNavajo();
		}
		
		for ( int i = 0; i < parameters.size(); i++ ) {
			Parameter p = parameters.get(i);
			String name = p.getName();
			ArrayList<ConditionalExpression> exps =  p.getExpressions();
			for (int j = 0; j < exps.size(); j++) {
				ConditionalExpression ce = exps.get(j);
				try {
					// Evaluate corresponding condition with expression.
					Navajo alt = null;
					if ( ce.hasDefinedSourceNavajo() ) {
						String state = ce.getSourceState();
						String reqresponse = ce.getSourceDirection();
						if ( myState != null && !state.equals(".") && !state.equals(myState.getId())) {
							State h = myState.getWorkFlow().getHistoricState(state);
							if ( h != null && h.getInitiatingAccess() != null ) {
								if ( reqresponse.equals("response") ) {
									alt = h.getInitiatingAccess().getOutputDoc();
								} else {
									alt = h.getInitiatingAccess().getInDoc();
								}
							}
						} else if ( state.equals(".") && t.getTrigger().getAccess() != null ){  // Use trigger access state.
							if ( reqresponse.equals("response") ) {
								alt =  t.getTrigger().getAccess().getOutputDoc();
							} else {
								alt =  t.getTrigger().getAccess().getInDoc();
							}
						} else if ( myState != null && state.equals(myState.getId() ) && myState != null && myState.getInitiatingAccess() != null ) {
							if ( reqresponse.equals("response") ) {
								alt =  myState.getInitiatingAccess().getOutputDoc();
							} else {
								alt =  myState.getInitiatingAccess().getInDoc();
							}
						}
					}
					if ( Condition.evaluate(ce.getCondition(), (alt != null ? alt : n ) ) ) {
						Operand o = Expression.evaluate(ce.getExpression(), (alt != null ? alt : n ) );
						usethisState.getWorkFlow().addParameter(name, o.value);
						j = exps.size() + 1;
					}
				} catch (TMLExpressionException e) {
					e.printStackTrace(System.err);
					WorkFlowManager.log(this.myState.getWorkFlow(), this, e.getMessage(), e);
					this.myState.getWorkFlow().setKill(true);
				} catch (SystemException e2) {
					e2.printStackTrace(System.err);
					WorkFlowManager.log(this.myState.getWorkFlow(), this, e2.getMessage(), e2);
					this.myState.getWorkFlow().setKill(true);
				}
			}
		}
	}
	
	private final boolean isMyTransitionTaskTrigger(Task t) {
		
		//synchronized ( semaphore ) {
			boolean result= ( t.getId().equals(myTask.getId()) &&
					( myState == null ||
							myState.getWorkFlow() == null ||
							( myState.getWorkFlow().getDefinition().equals(t.getWorkflowDefinition()) &&
									myState.getWorkFlow().getMyId().equals(t.getWorkflowId())
							)
					)
			);
			
			return result;
		//}
	}
	
	/**
	 * Activates a transition, e.g. registers associated task with the taskrunner manager and registers the transition
	 * as a task listener.
	 *
	 */
	public final void activate() {

		TaskRunner.getInstance().addTaskListener(this);
		if ( myState != null && myState.initiatingAccess != null ) {
			myTask.setUsername(myState.initiatingAccess.rpcUser);
			myTask.setPassword(myState.initiatingAccess.rpcPwd);
		}
		// If single event trigger, reinit trigger (to prevent time triggers from the past for offsettimes).
		if ( myTask.getTrigger().isSingleEvent() ) {
			try {
				myTask.setTrigger(trigger);
			} catch (UserException e) {
				e.printStackTrace(System.err);
			}
		}
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
	public final void cleanup() {
		TaskRunner.getInstance().removeTaskListener(this);
		TaskRunner.getInstance().removeTask(myTask.getId());
	}
	
	private final boolean isBeforeTrigger(Task t) {
		return t.getTriggerDescription().startsWith("beforenavajo");
	}
	
	private final boolean enterNextState(Task t) {
		
		if ( myCondition == null || myCondition.equals("") || t.getTrigger().getAccess() == null ) {
			return true;
		}
		Navajo n = ( isBeforeTrigger(t) ? t.getTrigger().getAccess().getInDoc() : t.getTrigger().getAccess().getOutputDoc() );
		// Merge this Navajo with localNavajo store of the workflow instance to use local workflow parameters.
		if ( myState != null ) {
			myState.getWorkFlow().mergeWithParameters(n);
		}
	
		try {
			boolean b =  Condition.evaluate(myCondition, n);
			return b;
		} catch (Throwable e) {
			// Could not evaluate condition, hence must return false.
			e.printStackTrace(System.err);
			return false;
		}
	}
	
	private final void doTaskStuff(Task t, Navajo request) {

		if ( !activationTranstion ) {
			// First evaluate all parameters that need to be set with this transition.
			evaluateParameters(t, myState);
			myState.leave();

			if ( nextState != null && !nextState.equals(Transition.FINISH) ) { 
				State ns = myState.getWorkFlow().createState(nextState, t.getTrigger().getAccess());
				if ( ns != null ) {
					ns.enter(true);
				}
			} else {
				myState.getWorkFlow().finish();
			}
		} else {
			/**
			 * If this is a special activationTranstion, do not clean up this transition, simply create new workflow.
			 */
			// Activate new workflow instance.
			WorkFlow wf = WorkFlow.getInstance(workFlowToBeActivated, nextState, t.getTrigger().getAccess(), t.getUsername());
			//myState = wf.currentState;
			evaluateParameters(t, wf.currentState);
			wf.start();
			//myState = wf.currentState;
			//myState = null;
		}
		
	}
	
	public final void afterTask(Task t, Navajo request) {

		if ( !isBeforeTrigger(t) && isMyTransitionTaskTrigger(t) && enterNextState(t) ) {
			
			doTaskStuff(t, request);
			
		}

	}

	public final boolean beforeTask(Task t, Navajo request) {

		if ( isBeforeTrigger(t) && isMyTransitionTaskTrigger(t) && enterNextState(t) ) {

			doTaskStuff(t, request);

		} 
		return true;

	}

	public final boolean isActivationTranstion() {
		return activationTranstion;
	}

	public final void setActivationTranstion(boolean activationTranstion) {
		this.activationTranstion = activationTranstion;
	}

	public final Task getMyTask() {
		return myTask;
	}

	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
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
