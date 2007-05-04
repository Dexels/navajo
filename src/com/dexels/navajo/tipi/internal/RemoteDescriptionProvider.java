package com.dexels.navajo.tipi.internal;

import java.util.ArrayList;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiContext;

public class RemoteDescriptionProvider extends BaseDescriptionProvider {

	public RemoteDescriptionProvider(TipiContext myContext) {
		super(myContext);
	}

	public Set getDescriptionNames() {
		return myDescriptionMap.keySet();
	}

	public void setMessage(Message m) {
		  ArrayList al = m.getAllMessages();
		   for (int i = 0; i < al.size(); i++) {
			   Message current = (Message)al.get(i);
			   String name = current.getProperty("Name").getValue();
			   String value = current.getProperty("Description").getValue();
			   addDescription(name, value);
		   }
		   System.err.println("# of descriptions: "+myDescriptionMap.size());
	}


//	public void init(String locale, String context) {
//		// TODO Auto-generated method stub
//		
//	}
}
