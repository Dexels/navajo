package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.enterprise.queue.Queuable;

public class QueuableFinishedEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8856473507658376031L;

	private Queuable myQueable;
	
	public QueuableFinishedEvent(Queuable q) {
		myQueable = q;
	}
	
	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Queuable getMyQueable() {
		return myQueable;
	}

}
