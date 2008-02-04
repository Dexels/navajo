package com.dexels.navajo.events.types;

import com.dexels.navajo.events.NavajoEvent;

public class NavajoHealthCheckEvent implements NavajoEvent {

	private String message;
	
	public NavajoHealthCheckEvent(String msg) {
		message = msg;
	}

	public String getMessage() {
		return message;
	}
	
}
