package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;

/**
 * NavajoRequestEvent is typically emitted when a new Navajo is received.
 * A typical Listener is the Navajo Dispatcher Class.
 * Event could be 'rerouted' to e.g. a Caching Dispatcher that emits
 * a new event NavajoCachedRequestEvent (e.g.) to which the Dispatcher Class will now listen.
 * The NavajoRequestEvent can also be listened to be arbitrary monitoring classes.
 * 
 * @author arjen
 *
 */
public class NavajoRequestEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719913277992819515L;
	
	private Navajo navajo;

	public NavajoRequestEvent(Navajo n) {
		this.navajo = n;
	}
	
	public Navajo getNavajo() {
		return navajo;
	}

	public Navajo getEventNavajo() {
		// TODO Auto-generated method stub
		return null;
	}
}
