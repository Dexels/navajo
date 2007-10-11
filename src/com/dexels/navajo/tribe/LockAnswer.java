package com.dexels.navajo.tribe;

import java.io.Serializable;

public class LockAnswer implements Answer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6037231721197484744L;
	
	public SharedStoreLock mySsl = null;
	
	public LockAnswer(SharedStoreLock ssl) {
		mySsl = ssl;
	}
	
	public boolean acknowledged() {
		return ( mySsl != null );
	}

}
