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
		return null;
	}

	@Override
	public void setStatus(PingAnswer pa) {
		
	}

	@Override
	public Date getJoinDate() {
		return null;
	}

	@Override
	public void setMemberName(String memberName) {
		
	}

	@Override
	public void setChief(boolean b) {
		
	}

	@Override
	public boolean isChief() {
		return false;
	}

	@Override
	public String getMemberId() {
		return null;
	}

    @Override
    public boolean isSafe() {
        return true;
    }

}
