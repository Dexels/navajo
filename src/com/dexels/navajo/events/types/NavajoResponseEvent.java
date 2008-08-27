package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.Access;

public class NavajoResponseEvent implements NavajoEvent {

	private Access myAccess;
	private Exception myException;
	
	public NavajoResponseEvent(Access a, Exception e) {
		this.myAccess = a;
		this.myException = e;
	}
	
 	public Access getAccess() {
		return myAccess;
	}

	public Exception getException() {
		return myException;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
