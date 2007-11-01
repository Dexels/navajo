package com.dexels.navajo.tribe;

import java.io.Serializable;

import com.dexels.navajo.server.Dispatcher;

/**
 * A LockQuestion is a request for a lock.
 * Also implement LockReleaseRequest...
 * 
 * @author arjen
 *
 */
public class GetLockRequest extends Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5986028260014271881L;
	
	public String parent;
	public String name;
	int lockType;
	private boolean block;
	
	private int lockTimeOut = 30000; // in millis
	
	public GetLockRequest(String parent, String name, int lockType, boolean block) {
		super();
		this.parent = parent;
		this.name = name;
		this.lockType = lockType;
		this.block = block;
	}
	
	public Answer getAnswer() {
		
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = ssi.lock(parent, name, lockType, block);
		
		return new LockAnswer(this, ssl);
		
	}

	public int getLockTimeOut() {
		return lockTimeOut;
	}

	public void setLockTimeOut(int lockTimeOut) {
		this.lockTimeOut = lockTimeOut;
	}
	
}
