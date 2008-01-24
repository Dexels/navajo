package com.dexels.navajo.events;

public interface NavajoListener {

	/**
	 * This method is invoked whenever an event occurs.
	 * 
	 * @param ne the specific NavajoEvent that occurred.
	 */
	public void onNavajoEvent(NavajoEvent ne);
	
}
