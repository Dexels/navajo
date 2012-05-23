package com.dexels.navajo.server.enterprise.queue;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Access;

public interface Queuable extends Serializable, QueuableMXBean {
	
    
	/**
     * Initiates the queued send operation.
     */ 
	public void setQueuedSend(boolean b);
    
	/**
     * Set maximum number of retries before giving up.
     */
	public void setMaxRetries(int r);
    
	/**
     * Set waiting time interval to use between retries.
     */
	public void setWaitUntil(long w);
	
	/**
	 * Get maximum number of retries before giving up.
	 */
	public int getMaxRetries();
	
	/**
	 * Get waiting time interval to use between retries.
	 * @return
	 */
	public long getWaitUntil();
	
	/**
	 * send() implements the work that should be done upon processing of the queable object.
	 * 
	 * @return true on success. Note that all exceptions (checked and un-checked) should be handled within the send() method.
	 */
	public boolean send();
	
	/**
	 * getResponse() will retrieve the response of the queable object when finished.
	 * 
	 * @return
	 */
	public Binary getResponse();
	
	/**
	 * getRequest() will return the request payload for to be used in queable object send() method.
	 * @return
	 */
	public Binary getRequest();
	
	/**
	 * Get access object associated with the request.
	 * 
	 * @return
	 */
	public Access getAccess();
	
	/**
	 * Get the navajo object associated with the request.
	 * 
	 * @return
	 */
	public Navajo getNavajo();
	
	/**
	 * Reset retry counter.
	 */
	public void resetRetries();
	
	/**
	 * Get number of processed retries thus far.
	 * 
	 * @return
	 */
	public int getRetries();
	
	/**
	 * Get the maximum number of specified instances that MAY run simultaneously.
	 * -1 if there is no limit (default)
	 * 
	 * @return
	 */
	public int getMaxRunningInstances();
	
	/**
	 * Sets the maximum number of specified instances that MAY run simultaneously.
	 * -1 if there is no limit (default)
	 *  
	 * @param maxRunningInstances
	 */
	public void setMaxRunningInstances(int maxRunningInstances);
}
