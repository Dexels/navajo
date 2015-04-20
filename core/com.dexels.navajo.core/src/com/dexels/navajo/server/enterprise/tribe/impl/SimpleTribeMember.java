package com.dexels.navajo.server.enterprise.tribe.impl;

import java.util.Date;

import com.dexels.navajo.server.enterprise.tribe.PingAnswer;
import com.dexels.navajo.server.enterprise.tribe.TribeMemberInterface;

public class SimpleTribeMember implements TribeMemberInterface {

	@Override
	public String getMemberName() {
		return "";
	}

	@Override
	public Object getAddress() {
		return null;
	}

	@Override
	public PingAnswer getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatus(PingAnswer pa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getJoinDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMemberName(String memberName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setChief(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isChief() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMemberId() {
		// TODO Auto-generated method stub
		return null;
	}

}
