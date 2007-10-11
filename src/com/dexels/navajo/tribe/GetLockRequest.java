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
	public String owner;
	int lockType;
	
	public GetLockRequest(String parent, String name, int lockType) {
		super();
		this.parent = parent;
		this.name = name;
		this.owner = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
		this.lockType = lockType;
	}

	public Answer getAnswer() {
		
		SharedStoreInterface ssi = SharedStoreFactory.getInstance();
		SharedStoreLock ssl = ssi.lock(parent, name, owner, lockType);
		
		return new LockAnswer(ssl);
		
	}
	
}
