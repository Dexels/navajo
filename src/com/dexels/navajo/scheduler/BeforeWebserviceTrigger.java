package com.dexels.navajo.scheduler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericThread;

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
