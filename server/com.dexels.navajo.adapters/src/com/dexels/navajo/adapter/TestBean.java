package com.dexels.navajo.adapter;

import java.util.Date;

public class TestBean {

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
	
}
