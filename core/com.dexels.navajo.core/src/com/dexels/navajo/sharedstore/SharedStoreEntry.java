package com.dexels.navajo.sharedstore;

import java.io.Serializable;

public class SharedStoreEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8552732542353067542L;
	
	protected long lastModified;
	protected long length;
	protected Serializable value;
	protected String contentType;
	protected String name;
	protected String parent;
	
	public SharedStoreEntry() {
		
	}
	
	public SharedStoreEntry(String parent, String name, long lastModified, String contentType, long length) {
		this.parent = parent;
		this.name = name;
		this.lastModified = lastModified;
		this.contentType = contentType;
		this.length = length;
	}

	protected SharedStoreEntry(String name, Serializable v) {
		this.value = v;
		this.name = name;
		lastModified = System.currentTimeMillis();
	}
	
	public long getLength() {
		return length;
	}

	public String getContentType() {
		return contentType;
	}

	public String getParent() {
		return parent;
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
	
	protected Serializable getValue() {
		return value;
	}
	
	protected void update(Serializable v) {
		this.value = v;
		lastModified = System.currentTimeMillis();
	}
}
