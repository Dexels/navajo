package com.dexels.navajo.scheduler.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.scheduler.triggers.WebserviceTrigger;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.workflow.WorkFlowManager;

public class ServiceEventAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6422874803712687196L;
	
	private Navajo proxy = null;
	private boolean acknowledged = false;
	
	public ServiceEventAnswer(ServiceEventRequest q) {
		super(q);
		WebserviceTrigger bwt = ((ServiceEventRequest) super.myRequest).getMyBwt();
		if ( bwt.getTask().getWorkflowId() == null || WorkFlowManager.getInstance().hasWorkflowId(bwt.getTask().getWorkflowId()) ) {
			proxy = bwt.perform();
			acknowledged = true;
		} else {
			acknowledged = false;
		}
	}
	
	@Override
	public boolean acknowledged() {
		return acknowledged;
	}

	public Navajo getProxy() {
		return proxy;
	}

}
