package com.dexels.navajo.adapter.icalmap;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class Attendee implements Mappable {

	public String name;
	public String email;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return "CN="+name + ":mailto:" + email;
	}

	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}
	
	
}
