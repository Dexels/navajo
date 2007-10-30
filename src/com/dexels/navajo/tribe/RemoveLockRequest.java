package com.dexels.navajo.tribe;

import java.io.Serializable;

import com.dexels.navajo.server.Dispatcher;

public class RemoveLockRequest extends Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4793070731846134259L;
	
	public String parent;
	public String name;
	int lockType;
	
	public RemoveLockRequest(String parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	@Override
	public Answer getAnswer() {
		
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = ssi.getLock(parent, name);
		//System.err.println("IN RemoveLockRequest(), getAnswer().....: " + ssl);
		if ( ssl != null ) {
			ssi.release(ssl);
		}
		return new LockAnswer(this, ssl);
		
	}

}
