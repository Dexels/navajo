package com.dexels.navajo.server.enterprise.tribe;

import java.util.Date;

public class DummyTribeMemberImpl implements TribeMemberInterface {

	public String getMemberName() {
		return "";
	}

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
