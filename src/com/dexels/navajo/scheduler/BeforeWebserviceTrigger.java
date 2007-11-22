package com.dexels.navajo.scheduler;

import java.io.Serializable;

public class BeforeWebserviceTrigger extends WebserviceTrigger implements Serializable {

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
		WebserviceListenerRegistry.getInstance().removeBeforeTrigger(this);
	}
	
	public void activateTrigger() {
		WebserviceListenerRegistry.getInstance().registerBeforeTrigger(this);
	}
	
}
