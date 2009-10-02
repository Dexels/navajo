package com.dexels.navajo.adapter.messagemap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class ResultMessage implements Mappable {

	private Message msg;
	private Message parentMsg;
	private Navajo myNavajo;
	
	public void setMessage(Message m) {
		this.msg = m;
	}
	
	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
		this.parentMsg = access.getCurrentOutMessage();
		this.myNavajo = access.getOutputDoc();
	}

	public void store() throws MappableException, UserException {
		Message copy = msg.copy(myNavajo);
		parentMsg.addMessage(copy);
	}

}
