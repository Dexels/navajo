package com.dexels.navajo.tribe;

import java.io.Serializable;

public abstract class Answer implements Serializable {
	
	public Request myRequest;
	
	public Answer(Request q) {
		myRequest = q;
	}
	
	public abstract boolean acknowledged();

	public Request getMyRequest() {
		return myRequest;
	}
	
}
