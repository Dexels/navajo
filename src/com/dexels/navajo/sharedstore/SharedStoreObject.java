package com.dexels.navajo.sharedstore;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SharedStoreObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5601982043661350208L;
	
	private String parent;
	private String name;
	
	public SharedStoreObject(String parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	public long getModificationTime() {
		return SharedStoreFactory.getInstance().lastModified(this.parent, this.name);
	}
	
	public void setModificationTime(long l) {
		SharedStoreFactory.getInstance().setLastModified(this.parent, this.name, l);
	}
	
	public void remove() {
		SharedStoreFactory.getInstance().remove(this.parent, this.name);
	}
	
	public OutputStream getOutputStream() throws SharedStoreException {
		return SharedStoreFactory.getInstance().getOutputStream(this.parent, this.name, false);
	}
	
	public InputStream getInputStream() throws SharedStoreException {
		return SharedStoreFactory.getInstance().getStream(this.parent, this.name);
	}
	
	public Object getObject() throws SharedStoreException {
		return SharedStoreFactory.getInstance().get(this.parent, this.name);
	}
	
	public String getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	
}
