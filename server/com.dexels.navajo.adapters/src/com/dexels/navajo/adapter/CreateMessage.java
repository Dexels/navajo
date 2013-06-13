package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.MappingException;
import com.dexels.navajo.script.api.UserException;

public class CreateMessage implements Mappable {

	//public String name = "";
	
	private Access myAccess;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
	}

	@Override
	public void store() throws MappableException, UserException {
	}

	@Override
	public void kill() {
	}
	
	public void setName(String name) throws MappingException  {
		Navajo n = myAccess.getOutputDoc();
		Message m = MappingUtils.addMessage(n, myAccess.getCurrentOutMessage(), name, null, 1, "simple", null)[0];
		myAccess.setCurrentOutMessage(m);
	}

}
