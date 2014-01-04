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
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public abstract class BaseDescriptionProvider implements DescriptionProviderInterface, Mappable {

	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseDescriptionProvider.class);
	private Message descriptionMessage = null;
	public abstract void updateProperty(Navajo in, Property element, String locale);

	
	@Override
	public void setDescriptionConfigMessage(Message descriptionMessage) {
		this.descriptionMessage = descriptionMessage;
	}


	@Override
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


	@Override
	public void deletePropertyContext(String locale, String context) {
		
	}


	@Override
	public void flushCache() {
		
	}


	@Override
	public void flushUserCache(String user) {
		
	}


	@Override
	public int getCacheSize() {
		return 0;
	}
	

	@Override
	public void updatePropertyDescription(PropertyDescription pd) {
		// TODO Auto-generated method stub

	}


	@Override
	public void updateDescription(String locale, String name,
			String description, String context, String username) {
		
	}


//	public void updatePropertyDescription(int id, String description) {
//		
//	}

	@Override
	public void kill() {
	}


	@Override
	public void load(Access access) throws MappableException, UserException {
	}


	@Override
	public void store() throws MappableException, UserException {
	}
	
}
