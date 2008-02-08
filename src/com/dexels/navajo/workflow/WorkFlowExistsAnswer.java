package com.dexels.navajo.workflow;

import com.dexels.navajo.tribe.Answer;
import com.dexels.navajo.tribe.Request;

public class WorkFlowExistsAnswer extends Answer {

	private String workflowName;
	private String stateExpression;
	private boolean exists;
	
	public WorkFlowExistsAnswer(Request q, String workflowName, String stateExpression) {
		super(q);
		this.workflowName = workflowName;
		this.stateExpression = stateExpression;
		exists =  WorkFlowManager.getInstance().existsWorkFlow(workflowName, stateExpression);
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
