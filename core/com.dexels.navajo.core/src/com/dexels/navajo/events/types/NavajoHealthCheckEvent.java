package com.dexels.navajo.events.types;

import java.util.logging.Level;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;


public class NavajoHealthCheckEvent implements NavajoEvent, LevelEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917699466935141060L;
	
	private String message;
	private Level level;
	
	public NavajoHealthCheckEvent(Level level, String msg) {
		message = msg;
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Level getLevel() {
		return level;
	}
	
}
