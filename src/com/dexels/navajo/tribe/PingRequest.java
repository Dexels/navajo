package com.dexels.navajo.tribe;

public class PingRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4319458596962421985L;

	@Override
	public Answer getAnswer() {
		return new PingAnswer(this);
	}

}
