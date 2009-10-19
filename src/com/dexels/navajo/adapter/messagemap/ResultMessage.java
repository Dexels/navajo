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
		Message copy = msg.copy(myNavajo);
		parentMsg.merge(copy);
	}

	public Object getProperty(String s) throws UserException {
		return getPropertyValue(s);
	}
	
	public Object getPropertyValue(String s) throws UserException {
		if ( msg.getProperty(s) != null ) {
			return msg.getProperty(s).getTypedValue();
		} else {
			throw new UserException(-1, "Exception in getting propertyValue for property: " + s);
		}
	}
	
	public void store() throws MappableException, UserException {
	}

}
