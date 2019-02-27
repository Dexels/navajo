package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.script.api.Access;

/**
 * NavajoRequestEvent is typically emitted when a new Navajo is received. A
 * typical Listener is the Navajo Dispatcher Class. Event could be 'rerouted' to
 * e.g. a Caching Dispatcher that emits a new event NavajoCachedRequestEvent
 * (e.g.) to which the Dispatcher Class will now listen. The NavajoRequestEvent
 * can also be listened to be arbitrary monitoring classes.
 * 
 * @author arjen
 * 
 */
public class NavajoRequestEvent implements NavajoEvent {
	private static final long serialVersionUID = 8719913277992819515L;

	private Access myAccess;

	public NavajoRequestEvent(Access a) {
		this.myAccess = a;
	}

	public Access getAccess() {
		return myAccess;
	}

	@Override
	public Navajo getEventNavajo() {
		return null;
	}
	@Override
    public boolean isSynchronousEvent() {
        return false;
    }

}
