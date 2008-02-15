package com.dexels.navajo.scheduler;

import com.dexels.navajo.tribe.Answer;

public class AfterWebServiceAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3939068318168197624L;

	
	public AfterWebServiceAnswer(AfterWebServiceRequest q) {
		super(q);
		WebserviceListenerRegistry.getInstance().afterWebservice(q.getWebservice(), q.getMyAccess(), q.getIgnoreTaskIds(), true);
	}
	
	@Override
	public boolean acknowledged() {
		return true;
	}

}
