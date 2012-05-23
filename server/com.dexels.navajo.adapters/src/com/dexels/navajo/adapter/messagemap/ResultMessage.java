package com.dexels.navajo.adapter.messagemap;

import java.util.ArrayList;
import java.util.Iterator;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class ResultMessage implements Mappable {

	private Message msg;
	private Message parentMsg;
	private Navajo myNavajo;
	public Message getMsg() {
		return msg;
	}

	private String suppressProperties = null;
	
	public void setMessage(Message m, String suppressProperties) {
		this.msg = m;
		this.suppressProperties = suppressProperties;
	}
	
	private final boolean isPropertyInList(Property prop, String propertyStringList, boolean isArrayMessageElement) {
		if ( propertyStringList == null ) {
			return false;
		}
		String [] propertyList = propertyStringList.split(";");
		for (int i = 0; i < propertyList.length; i++) {
			if ( propertyList[i].equals(prop.getName())) {
				return true;
			}
		}
		return false;
	}
	
	private final void processSuppressedProperties(Message m) {
		Iterator<Property> allProps = new ArrayList<Property>(m.getAllProperties()).iterator();
		while ( allProps.hasNext() ) {
			Property p = (Property) allProps.next();
			if ( isPropertyInList(p, this.suppressProperties, m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) ) {
				m.removeProperty(p);
			}
		}
		Iterator<Message> subMessages = m.getAllMessages().iterator();
		while ( subMessages.hasNext() ) {
			processSuppressedProperties(subMessages.next());
		}
	}
	
	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
		this.parentMsg = access.getCurrentOutMessage();
		this.myNavajo = access.getOutputDoc();
		Message copy = msg.copy(myNavajo);
		processSuppressedProperties(copy);
		parentMsg.merge(copy);
	}

	public boolean getExists(String s) {
		try {
			getProperty(s);
			return true;
		} catch (Exception e) {
			return false;
		}
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
		processSuppressedProperties(this.msg); 
	}

}
