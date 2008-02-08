package com.dexels.navajo.workflow;

import com.dexels.navajo.tribe.Answer;
import com.dexels.navajo.tribe.Request;

public class WorkFlowExistsRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1987805385025802661L;
	
	private String workflowName;
	private String stateExpression;
	
	public WorkFlowExistsRequest(String workflowName, String stateExpression) {
		this.workflowName = workflowName;
		this.stateExpression = stateExpression;
	}
	
	@Override
	public Answer getAnswer() {
		return new WorkFlowExistsAnswer(this, workflowName, stateExpression);
	}

}
