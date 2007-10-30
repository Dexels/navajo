package com.dexels.navajo.tribe;

import java.io.Serializable;

public class SharedStoreLock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982466905008510851L;
	
	private int lockTimeOut = 10000; // in millis
	
	public SharedStoreLock(String name, String parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public String owner;
	public String parent;
	public String name;
	public int lockType;
	
	public String toString() {
		return "(" + owner + "," + parent + "," + name + ")";
	}

	public int getLockTimeOut() {
		return lockTimeOut;
	}

	/**
	 * Specify lock timeout in millis.
	 * Default value is 10000.
	 * 
	 * @param lockTimeOut
	 */
	public void setLockTimeOut(int lockTimeOut) {
		this.lockTimeOut = lockTimeOut;
	}
}
