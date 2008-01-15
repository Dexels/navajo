package com.dexels.navajo.server.descriptionprovider;

import java.util.ArrayList;
import java.util.Iterator;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;

public abstract class BaseDescriptionProvider implements DescriptionProviderInterface, Mappable {

	private Message descriptionMessage = null;

	public abstract void updateProperty(Navajo in, Property element, String locale);

	
	public void setDescriptionConfigMessage(Message descriptionMessage) {
		this.descriptionMessage = descriptionMessage;
	}


	public void updatePropertyDescriptions(Navajo in, Navajo out) throws NavajoException {
		String locale = in.getHeader().getHeaderAttribute("locale");
		//System.err.println("Locale: "+locale);
		if (locale==null) {
			return;
		}
		Header outHeader = out.getHeader();
		Header inHeader = in.getHeader();
		if(inHeader==null) {
			System.err.println("No IN header found. Thats is weird");
			return;
		} else {
			outHeader.setHeaderAttribute("locale", locale);
		}
		 
		if(outHeader==null) {
			System.err.println("No header found. Thats a problem");
//			outHeader = NavajoFactory.getInstance().createHeader(in, locale, locale, locale, getCacheSize())
		} else {
			outHeader.setHeaderAttribute("locale", locale);
		}
//		System.err.println("Updating navajo. Locale: "+locale);
//		System.err.println("user: "+in.getHeader().getRPCUser());

		ArrayList a = out.getAllMessages();
		for (Iterator iter = a.iterator(); iter.hasNext();) {
			Message element = (Message) iter.next();
			updateMessage(in,element,locale);
		}
	}

	private void updateMessage(Navajo in, Message m,String locale) {
		ArrayList a = m.getAllMessages();
		for (Iterator iter = a.iterator(); iter.hasNext();) {
			Message element = (Message) iter.next();
			updateMessage(in,element,locale);
		}		
		ArrayList b = m.getAllProperties();
		for (Iterator iter = b.iterator(); iter.hasNext();) {
			Property element = (Property) iter.next();
			updateProperty(in,element,locale);
		}				
	}


	public Message getDescriptionMessage() {
		return descriptionMessage;
	}


	public void deletePropertyContext(String locale, String context) {
		// TODO Auto-generated method stub
		
	}


	public void flushCache() {
		// TODO Auto-generated method stub
		
	}


	public void flushUserCache(String user) {
		// TODO Auto-generated method stub
		
	}


	public int getCacheSize() {
		// TODO Auto-generated method stub
		return 0;
	}


	public void updateDescription(String locale, String name,
			String description, String context, String username) {
		// TODO Auto-generated method stub
		
	}


	public void updatePropertyDescription(int id, String description) {
		// TODO Auto-generated method stub
		
	}

	public void kill() {
	}


	public void load(Parameters parms, Navajo inMessage, Access access,	NavajoConfig config) throws MappableException, UserException {
	}


	public void store() throws MappableException, UserException {
	}
	
}
