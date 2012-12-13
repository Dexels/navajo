package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.document.Navajo;

/**
 * The Listener interface.
 * 
 * @author arjen
 *
 */
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
	
	/**
	 * Defines whether this listener is only used in 'local', i.e. in-memory, mode. 
	 * 
	 * @return
	 */
	public boolean isLocal();
	
	public void setLocal();
	
	
}
