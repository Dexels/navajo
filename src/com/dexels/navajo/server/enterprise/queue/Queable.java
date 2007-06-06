package com.dexels.navajo.server.enterprise.queue;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Access;

public interface Queable extends Serializable, QueableMXBean {
	
	/**
	 * Public setters to be used from web service script.
	 */
    // Public method to be used from web service script.
	public void setQueuedSend(boolean b);
    // Set maximum number of retries before giving up.
	public void setMaxRetries(int r);
    // Set waiting time interval to use between retries.
	public void setWaitUntil(long w);
	
	/**
	 * Public getters/setters to be used by requestresponse queue for lifecycle management and administration.
	 */
    // Get maximum number of retries before giving up.
	public int getMaxRetries();
    // Get waiting time interval to use between retries.
	public long getWaitUntil();
	
	// send() should implement the work that should be done upon processing of the queable object.
	public boolean send();
	// getResponse() will retrieve the response of the queable object when finished.
	public Binary getResponse();
	// getRequest() will return the request payload for to be used in queable object send() method.
	public Binary getRequest();
	
	// Get access object associated with the request.
	public Access getAccess();
	// Get tha navajo object associated with the request.
	public Navajo getNavajo();
	
	// Reset retry counter.
	public void resetRetries();
	// Get number of processed retries thus far.
	public int getRetries();
	
	// Method that can be used to prevent garbage collection of binary placeholder files.
	public void persistBinaries();
	// Method that can be used to flag garbage collection of binary placeholder files.
	public void removeBinaries();
}
