/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore;

import java.io.Serializable;

public class SharedStoreEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8552732542353067542L;
	
	protected long lastModified;
	protected long length;
	protected Object value;
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
	
	protected SharedStoreEntry(String name, byte [] value) {
		this.value = value;
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
	
	protected Object getValue() {
		return value;
	}
	
	protected void update(Serializable v) {
		this.value = v;
		lastModified = System.currentTimeMillis();
	}
}
