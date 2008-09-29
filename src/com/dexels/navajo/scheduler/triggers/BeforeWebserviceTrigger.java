package com.dexels.navajo.scheduler.triggers;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.scheduler.WebserviceListenerRegistry;

public class BeforeWebserviceTrigger extends AfterWebserviceTrigger implements Serializable {

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
	
	/**
	 * Always perform task synchronously for before webservice trigger.
	 */
	public Navajo perform() {
		try {
			getTask().run();
		} catch (Throwable t2) {
			t2.printStackTrace(System.err);
		}
		return getTask().getResponse();
	}
	
}
