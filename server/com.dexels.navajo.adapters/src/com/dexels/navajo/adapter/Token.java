package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class Token implements Mappable {
  public String value;
	
	public void kill() {

	}

	public void load(Access access) throws MappableException, UserException {

	}

	public void store() throws MappableException, UserException {

	}
	
	public String getValue(){
		return value;
	}

}
