package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;

public class NavajoHealthCheckEvent implements NavajoEvent {

	private String message;
	
	public NavajoHealthCheckEvent(String msg) {
		message = msg;
	}

	public String getMessage() {
		return message;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
