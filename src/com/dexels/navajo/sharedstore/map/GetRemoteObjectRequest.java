package com.dexels.navajo.sharedstore.map;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class GetRemoteObjectRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 467290545887579503L;
	
	private String guid = null;
	
	public GetRemoteObjectRequest(RemoteReference remote) {
		guid = remote.getRef();
	}
	
	@Override
	public Answer getAnswer() {
		Object o = RemoteReference.getObject(guid);
		System.err.println("in getAnswer(): GETTING OBJECT FOR GUID: " + guid + ", o = " + o);
		return new GetRemoteObjectAnswer(this, o);
	}

}
