package com.dexels.navajo.adapter.queue;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Access;

public interface Queable extends Serializable, QueableMXBean {
	
	// send() should implement the work that should be done upon processing of the queable object.
	public boolean send();
	// getResponse() will retrieve the response of the queable object when finished.
	public Binary getResponse();
	// getRequest() will return the request payload for to be used in queable object send() method.
	public Binary getRequest();
	
	public Access getAccess();
	public Navajo getNavajo();
	
	// Public method to be used from web service script.
	public void setQueuedSend(boolean b);
	
	// Set waiting time interval to use between retries.
	public void setWaitUntil(long w);
	public long getWaitUntil();
	
	// Set maximum number of retries before giving up.
	public void setMaxRetries(int r);
	public int getMaxRetries();
	public void resetRetries();
	// Get number of processed retries thus far.
	public int getRetries();
	
	// Method that can be used to prevent garbage collection of binary placeholder files.
	public void persistBinaries();
	// Method that can be used to flag garbage collection of binary placeholder files.
	public void removeBinaries();
}
