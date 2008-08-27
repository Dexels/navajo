package com.dexels.navajo.events;

import com.dexels.navajo.document.Navajo;

/**
 * A NavajoEvent is used as a means of communicating between different parts (modules) of a Navajo server.
 * A NavajoEvents enables a flexible a modularized architecture for the Navajo 2.0 system.
 * NavajoEvents are defined as light-weight "internal" events. They differ from e.g. web service events which are communicated
 * to all members of a Navajo cluster.
 * 
 * @author arjen
 *
 */
public interface NavajoEvent {

	/**
	 * Returns a Navajo object with the relevant event parameters as properties in
	 * a message named "__event__".
	 * 
	 * @return
	 */
	public Navajo getEventNavajo();
	
}
