package com.dexels.navajo.enterprise.scheduler.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerFactory;
import com.dexels.navajo.server.enterprise.tribe.Answer;

public class BeforeWebServiceAnswer extends Answer {

	private Navajo myNavajo;
	
	public BeforeWebServiceAnswer(BeforeWebServiceRequest q) {
		super(q);
		// Emit 'beforeWebservice'.
		myNavajo = WebserviceListenerFactory.getInstance().beforeWebservice(q.getWebservice(), q.getMyAccess(), q.getIgnoreTaskIds(), true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110911541531797766L;

	@Override
	public boolean acknowledged() {
		return true;
	}

	public Navajo getMyNavajo() {
		return myNavajo;
	}

}
