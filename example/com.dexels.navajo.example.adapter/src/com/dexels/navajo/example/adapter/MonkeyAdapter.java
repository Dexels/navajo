package com.dexels.navajo.example.adapter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class MonkeyAdapter implements Mappable {

	@Override
	public void load(Access access) throws MappableException, UserException {
		Navajo out = access.getOutputDoc();
		Message monkey = NavajoFactory.getInstance().createMessage(out, "Monkey");
		Binary b = new Binary(getClass().getResourceAsStream("monkey.jpeg"));
		Property p = NavajoFactory.getInstance().createProperty(out, "Monkey", Property.BINARY_PROPERTY,null,-1,"",Property.DIR_OUT);
		monkey.addProperty(p);
		p.setAnyValue(b);
		out.addMessage(monkey);
	}

	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

}
