package com.dexels.navajo.scheduler;

public class BeforeWebserviceTrigger extends WebserviceTrigger {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3077411672254731436L;

	public BeforeWebserviceTrigger(String description) {
		super(description);
	}
	
	public String getDescription() {
		return Trigger.WS_BEFORE_TRIGGER + ":" + myDescription;
	}
	
	public void removeTrigger() {
		// Remove myself from the listener list.
		System.err.println("REMOVING BEFORE WEBSERVICE   TRIGGER: " + getDescription());
		WebserviceListener.getInstance().removeBeforeTrigger(this);
	}
	
	public void activateTrigger() {
		System.err.println("ACTIVATING BEFORE WEBSERVICE TRIGGER: " + getDescription());
		WebserviceListener.getInstance().registerBeforeTrigger(this);
	}
}
