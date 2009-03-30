package com.dexels.navajo.mapping.bean;

import java.util.Date;

public class TestBean {

	public String lastname;
	public String firstname;
	public Date dateOfBirth = new java.util.Date();
	public boolean isVip = false;
	public int num;
	public double dub;
	public float flt;
	
	public String getLastname() {
		return lastname;
	}
	
	public String getAppendedLastname(String noot) {
		return lastname + "/" + noot;
	}
	
	public void setLastname(String lastname) {
		System.err.println("In TestBean.setLastName: " + lastname);
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public boolean getIsVip() {
		return isVip;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getDub() {
		return dub;
	}

	public void setDub(double dub) {
		this.dub = dub;
	}

	public float getFlt() {
		return flt;
	}

	public void setFlt(float flt) {
		this.flt = flt;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}
	
}
