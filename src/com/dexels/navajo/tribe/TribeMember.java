package com.dexels.navajo.tribe;

import java.io.Serializable;
import java.util.Date;

import org.jgroups.Address;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TribeMember implements Serializable, Mappable {

	private static final long serialVersionUID = -1371503985787191894L;
	
	public String memberName;
	private Address address;
	public boolean isChief;
	public Date joinDate;
	public PingAnswer status;
	
	public TribeMember(String s, Address a) {
		this.memberName = s;
		this.address = a;
		this.joinDate = new Date();
	}
	
	public String getMemberName() {
		return memberName;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public boolean getIsChief() {
		return isChief;
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

	public PingAnswer getStatus() {
		return status;
	}

	public void setStatus(PingAnswer status) {
		this.status = status;
	}

	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

}
