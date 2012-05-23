package com.dexels.navajo.events.types;

import javax.management.AttributeChangeNotification;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;

public class ChangeNotificationEvent implements NavajoEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6313135216963337700L;

	private AttributeChangeNotification myNotification;
	
	public ChangeNotificationEvent(String source, String message, String attribute, String type, Object prefValue, Object newValue) {
		  
		myNotification = new AttributeChangeNotification(
				            NavajoEventRegistry.getInstance(), 
						    NavajoEventRegistry.notificationSequence++, 
						    System.currentTimeMillis(), 
						    source +":"+message, 
						    attribute, 
						    type, 
						    prefValue, 
						    newValue); 
	}
	
	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}

	public AttributeChangeNotification getJMXNotification() {
		return myNotification;
	}

}
