package com.dexels.twitter;

import winterwell.jtwitter.Twitter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class TwitterAdapter implements Mappable {
	private String username, password;
	private Access myAccess;
	

	public void kill() {
	}


	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
	}


	public void store() throws MappableException, UserException {
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setStatus(String status){
		String statusText = status;
		if(statusText.length() > 140){
			statusText = statusText.substring(0,140);
		}
		try{
			Twitter twit = new Twitter(username, password);
			twit.setSource("Navajo Integrator");
			twit.updateStatus(statusText);			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

}
