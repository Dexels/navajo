package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class Token implements Mappable {
  public String value;
	
	@Override
	public void kill() {

	}

	@Override
	public void load(Access access) throws MappableException, UserException {

	}

	@Override
	public void store() throws MappableException, UserException {

	}
	
	public String getValue(){
		return value;
	}

}
