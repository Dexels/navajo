package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;

public class ImmediateTrigger extends Trigger {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1601015782092026337L;

	
	@Override
	public void activateTrigger() {
		System.err.println("IN ImmediateTrigger() activateTrigger()");
		perform();
	}

	@Override
	public String getDescription() {
		return Trigger.IMMEDIATE_TRIGGER;
	}

	@Override
	public boolean isSingleEvent() {
		return true;
	}

	@Override
	public void removeTrigger() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSingleEvent(boolean b) {
		// TODO Auto-generated method stub

	}

	public Navajo perform() {
		getTask().run();
		return null;
	}

}
