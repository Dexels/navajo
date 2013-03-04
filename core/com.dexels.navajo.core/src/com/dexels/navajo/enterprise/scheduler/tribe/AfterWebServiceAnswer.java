package com.dexels.navajo.enterprise.scheduler.tribe;

import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerFactory;
import com.dexels.navajo.server.enterprise.tribe.Answer;

public class AfterWebServiceAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3939068318168197624L;

	
	public AfterWebServiceAnswer(AfterWebServiceRequest q) {
		super(q);
		WebserviceListenerFactory.getInstance().afterWebservice(q.getWebservice(), q.getMyAccess(), q.getIgnoreTaskIds(), true);
	}
	
	@Override
	public boolean acknowledged() {
		return true;
	}

}
