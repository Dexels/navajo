package com.dexels.navajo.server.descriptionprovider;

import java.util.ArrayList;
import java.util.Iterator;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;

public abstract class BaseDescriptionProvider implements DescriptionProvider{

	public abstract void updateProperty(Navajo in, Property element, String locale);

	


	public void updatePropertyDescriptions(Navajo in, Navajo out) throws NavajoException {
		String locale = in.getHeader().getAttribute("locale");
		if (locale==null) {
			return;
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
	



}
