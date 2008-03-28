package com.dexels.navajo.functions;

import java.util.Iterator;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeMemberInterface;
import com.dexels.navajo.workflow.WorkFlowExistsAnswer;
import com.dexels.navajo.workflow.WorkFlowExistsRequest;


public class ExistsWorkflow extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		if ( getOperands().size() != 3 ) {
			throw new TMLExpressionException(this, "Invalid number of parameters");
		}
		
		if ( !(getOperand(0) instanceof String) || !(getOperand(1) instanceof String) )  {
			throw new TMLExpressionException(this, "Invalid arguments: " + getOperands().toString());
		}
		
		String workflowName = (String) getOperand(0);
		String stateName = ( getOperand(1) == null ? null : (String) getOperand(1) );
		String stateExpression = (String) getOperand(2);
	
		Iterator<TribeMemberInterface> all = TribeManagerFactory.getInstance().getAllMembers().iterator();
		
		while ( all.hasNext() ) {
			WorkFlowExistsRequest wer = new WorkFlowExistsRequest(workflowName, stateName, stateExpression);
			TribeMemberInterface tm = all.next();
			WorkFlowExistsAnswer wea = (WorkFlowExistsAnswer) TribeManagerFactory.getInstance().askSomebody(wer, tm.getAddress());
			if ( wea.isExists() ) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	@Override
	public String remarks() {
		return "Check whether a workflow with specified name and given global state exists";
	}

	@Override
	public String usage() {
		return "ExistsWorkflow([workflow name], [workflow state], [global state check expression]";
	}

}
