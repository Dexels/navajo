package com.dexels.navajo.tribe;

import java.io.Serializable;

public class SharedStoreLock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982466905008510851L;
	
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
}
