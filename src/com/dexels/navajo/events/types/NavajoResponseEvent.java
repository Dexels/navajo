package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.Access;

public class NavajoResponseEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8810423005552534253L;
	
	private Access myAccess;
	private Throwable myException;
	
	public NavajoResponseEvent(Access a, Throwable e) {
		this.myAccess = a;
		this.myException = e;
	}
	
 	public Access getAccess() {
		return myAccess;
	}

	public Throwable getException() {
		return myException;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
