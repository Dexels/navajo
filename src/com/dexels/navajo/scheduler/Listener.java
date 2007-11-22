package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;

public interface Listener  {
	
	public String getListenerId();

	/**
	 * Perform the task that is associated with this listener.
	 * 
	 * @param a
	 * @return
	 */
	public Navajo perform();
	
	
	/**
	 * Get owner host.
	 * 
	 * @return
	 */
	public String getOwnerHost();
	
}
