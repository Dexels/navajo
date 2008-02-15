package com.dexels.navajo.workflow;

import com.dexels.navajo.tribe.Answer;
import com.dexels.navajo.tribe.Request;

public class WorkFlowExistsRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987805385025802661L;
	
	private String workflowName;
	private String stateName;
	private String stateExpression;
	
	public WorkFlowExistsRequest(String workflowName, String stateName, String stateExpression) {
		this.workflowName = workflowName;
		this.stateExpression = stateExpression;
		this.stateName = stateName;
	}
	
	@Override
	public Answer getAnswer() {
		return new WorkFlowExistsAnswer(this, workflowName, stateName, stateExpression);
	}

}
