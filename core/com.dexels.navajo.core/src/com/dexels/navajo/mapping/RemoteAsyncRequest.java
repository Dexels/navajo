package com.dexels.navajo.mapping;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class RemoteAsyncRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10518850435818645L;

	private String ref;
	
	public RemoteAsyncRequest(String ref) {
		this.ref = ref;
	}
	
	@Override
	public Answer getAnswer() {
		return new RemoteAsyncAnswer(this);
	}

	public String getRef() {
		return ref;
	}

}
