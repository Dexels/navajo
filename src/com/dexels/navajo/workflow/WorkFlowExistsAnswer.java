package com.dexels.navajo.workflow;

import com.dexels.navajo.tribe.Answer;
import com.dexels.navajo.tribe.Request;

public class WorkFlowExistsAnswer extends Answer {

	private String workflowName;
	private String stateName;
	private String stateExpression;
	private boolean exists;
	
	public WorkFlowExistsAnswer(Request q, String workflowName, String stateName, String stateExpression) {
		super(q);
		this.workflowName = workflowName;
		this.stateName = stateName;
		this.stateExpression = stateExpression;
		exists =  WorkFlowManager.getInstance().existsWorkFlow(workflowName, stateName, stateExpression);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3210702049272127624L;

	@Override
	public boolean acknowledged() {
		return true;
	}

	public boolean isExists() {
		return exists;
	}

}
