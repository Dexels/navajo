package com.dexels.navajo.adapter;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

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
