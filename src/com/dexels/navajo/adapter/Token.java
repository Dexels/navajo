package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class Token implements Mappable {
  public String value;
	
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}
	
	public String getValue(){
		return value;
	}

}
