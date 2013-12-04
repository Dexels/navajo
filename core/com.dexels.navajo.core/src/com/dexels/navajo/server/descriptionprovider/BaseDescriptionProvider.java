package com.dexels.navajo.server.descriptionprovider;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public abstract class BaseDescriptionProvider implements DescriptionProviderInterface, Mappable {

	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseDescriptionProvider.class);
	private Message descriptionMessage = null;
	public abstract void updateProperty(Navajo in, Property element, String locale);

	
	public void setDescriptionConfigMessage(Message descriptionMessage) {
		this.descriptionMessage = descriptionMessage;
	}


	public void updatePropertyDescriptions(Navajo in, Navajo out) throws NavajoException {
		String locale = in.getHeader().getHeaderAttribute("locale");
		if (locale==null) {
			return;
		}
		Header outHeader = out.getHeader();
		Header inHeader = in.getHeader();
		if(inHeader==null) {
			logger.warn("No IN header found. Thats is weird");
			return;
		} else {
			outHeader.setHeaderAttribute("locale", locale);
		}
		 

		List<Message> a = out.getAllMessages();
		for (Iterator<Message> iter = a.iterator(); iter.hasNext();) {
			Message element =  iter.next();
			updateMessage(in,element,locale);
		}
	}

	private void updateMessage(Navajo in, Message m,String locale) {
		List<Message> a = m.getAllMessages();
		for (Iterator<Message> iter = a.iterator(); iter.hasNext();) {
			Message element = iter.next();
			updateMessage(in,element,locale);
		}		
		List<Property> b = m.getAllProperties();
		for (Iterator<Property> iter = b.iterator(); iter.hasNext();) {
			Property element =  iter.next();
			updateProperty(in,element,locale);
		}				
	}


	public Message getDescriptionMessage() {
		return descriptionMessage;
	}


	public void deletePropertyContext(String locale, String context) {
		
	}


	public void flushCache() {
		
	}


	public void flushUserCache(String user) {
		
	}


	public int getCacheSize() {
		return 0;
	}
	

	@Override
	public void updatePropertyDescription(PropertyDescription pd) {
		// TODO Auto-generated method stub

	}


	public void updateDescription(String locale, String name,
			String description, String context, String username) {
		
	}


//	public void updatePropertyDescription(int id, String description) {
//		
//	}

	public void kill() {
	}


	public void load(Access access) throws MappableException, UserException {
	}


	public void store() throws MappableException, UserException {
	}
	
}
