package com.dexels.navajo.adapter;

import java.util.Date;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestBean implements Mappable {

	private String monkey = "Aap";
	private Date today = new Date();
	
	public void setMonkey(String s) {
		this.monkey = s;
	}
	
	public String getMonkey() {
		return this.monkey;
	}
	
	public Date getToday() {
		return this.today;
	}
	
	public void setToday(Date d) {
		this.today = d;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		
	}

	@Override
	public void kill() {
		
	}
	
}
