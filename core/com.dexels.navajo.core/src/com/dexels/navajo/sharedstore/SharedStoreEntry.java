package com.dexels.navajo.sharedstore;

import java.io.Serializable;

public class SharedStoreEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8552732542353067542L;
	
	protected long lastModified;
	protected Serializable value;
	protected String name;
	
	public SharedStoreEntry() {
		
	}
	
	protected SharedStoreEntry(String name, Serializable v) {
		this.value = v;
		this.name = name;
		lastModified = System.currentTimeMillis();
	}
	
	public String getName() {
		return name;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long l) {
		this.lastModified = l;
	}
	
	public Serializable getValue() {
		return value;
	}
	
	public void update(Serializable v) {
		this.value = v;
		lastModified = System.currentTimeMillis();
	}
}
