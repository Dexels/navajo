/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	
	@Override
	public Navajo getEventNavajo() {
		return null;
	}

	public AttributeChangeNotification getJMXNotification() {
		return myNotification;
	}

	@Override
    public boolean isSynchronousEvent() {
        return false;
    }
}
