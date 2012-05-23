package com.dexels.navajo.events;

/**
 * Interface that is used to implement a Navajo Event Listener.
 * 
 * @author arjen
 *
 */
public interface NavajoListener {

	/**
	 * This method is invoked whenever an event occurs.
	 * 
	 * @param ne the specific NavajoEvent that occurred.
	 */
	public void onNavajoEvent(NavajoEvent ne);
	
}
