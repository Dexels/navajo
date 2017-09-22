package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

public class Wrapper implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1776008626104972375L;
	private final String reference;
	private long lastuse;
	public int count;
	
	public Wrapper(String reference, long created) {
		this.reference = reference;
		count = 1;
	}
	
	public Wrapper(String reference, long created, int c) {
		this.reference = reference;
		count = c;
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
	
	public void increaseReference(int c) {
		this.lastuse = System.currentTimeMillis();
		count = count + c;
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

