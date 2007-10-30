package com.dexels.navajo.tribe;

import java.io.Serializable;
import java.util.Date;

import org.jgroups.Address;

public class TribeMember implements Serializable {

	private static final long serialVersionUID = -1371503985787191894L;
	
	private String name;
	private Address address;
	private boolean isChief;
	private Date joinDate;
	
	public TribeMember(String s, Address a) {
		this.name = s;
		this.address = a;
		this.joinDate = new Date();
	}
	
	public String getName() {
		return name;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public boolean isChief() {
		return isChief;
	}
	
	public void setChief(boolean b) {
		isChief = b;
	}

	public Date getJoinDate() {
		return joinDate;
	}

}
