package com.dexels.navajo.tribe;

import com.dexels.navajo.tribe.map.RemoteReference;

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
