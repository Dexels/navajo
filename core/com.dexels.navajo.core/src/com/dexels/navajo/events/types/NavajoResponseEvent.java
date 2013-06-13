package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.script.api.Access;

public class NavajoResponseEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8810423005552534253L;
	
	private Access myAccess;
	
	public NavajoResponseEvent(Access a) {
		this.myAccess = a;
	}
	
 	public Access getAccess() {
		return myAccess;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
