package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.enterprise.queue.Queuable;

public class QueuableFailureEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8856473507658376031L;

	private Queuable myQueable;
	
	public QueuableFailureEvent(Queuable q) {
		myQueable = q;
	}
	
	@Override
	public Navajo getEventNavajo() {
		return null;
	}

	public Queuable getMyQueable() {
		return myQueable;
	}

	@Override
    public boolean isSynchronousEvent() {
        return false;
    }
}
