package com.dexels.navajo.tribe;

public class RemoveLockRequest extends Request {

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
		this.blocking = false;
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
