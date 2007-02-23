package com.dexels.navajo.adapter.queue;

import java.io.Serializable;

import com.dexels.navajo.document.types.Binary;

public interface Queable extends Serializable {
	
	// send() should implement the work that should be done upon processing of the queable object.
	public boolean send();
	// getResponse() will retrieve the response of the queable object when finished.
	public Binary getResponse();
	// getRequest() will return the request payload for to be used in queable object send() method.
	public Binary getRequest();
	
	// Public method.
	public void setQueuedSend(boolean b);
	
}
