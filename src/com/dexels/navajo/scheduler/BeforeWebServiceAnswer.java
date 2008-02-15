package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tribe.Answer;

public class BeforeWebServiceAnswer extends Answer {

	private Navajo myNavajo;
	
	public BeforeWebServiceAnswer(BeforeWebServiceRequest q) {
		super(q);
		myNavajo = WebserviceListenerRegistry.getInstance().beforeWebservice(q.getWebservice(), q.getMyAccess(), q.getIgnoreTaskIds(), true);
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
