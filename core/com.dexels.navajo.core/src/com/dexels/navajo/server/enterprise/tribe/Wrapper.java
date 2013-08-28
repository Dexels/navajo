package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

public class Wrapper implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1776008626104972375L;
	private final String reference;
	private final long created;
	private long lastuse;
	public int count;
	
	public Wrapper(String reference, long created) {
		this.reference = reference;
		this.created = this.lastuse = created;
		count = 1;
	}

	public int getCount() {
		return count;
	}

	public String getReference() {
		return reference;
	}
	
	public void resetCount() {
		count = 0;
	}
	
	public void increaseReference() {
		this.lastuse = System.currentTimeMillis();
		count++;
	}
	
	public void decreaseReference() {
		if ( count > 0 ) {
			count--;
		}
	}
	
	public long getLastUse() {
		return lastuse;
	}
	
}

