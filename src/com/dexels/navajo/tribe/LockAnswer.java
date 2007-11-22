package com.dexels.navajo.tribe;


public class LockAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6037231721197484744L;
	
	public SharedStoreLock mySsl = null;
	
	public LockAnswer(Request q, SharedStoreLock ssl) {
		super(q);
		mySsl = ssl;
	}
	
	public boolean acknowledged() {
		return ( mySsl != null );
	}

}
