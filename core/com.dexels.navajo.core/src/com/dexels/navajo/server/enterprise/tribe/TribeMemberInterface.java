package com.dexels.navajo.server.enterprise.tribe;

import java.util.Date;

public interface TribeMemberInterface {

	public String getMemberName();
	// Following method is added to support non-named tribal members.
	public String getMemberId();
	public Object getAddress();
	public PingAnswer getStatus();
	public void setStatus(PingAnswer pa);
	public Date getJoinDate();
	public void setMemberName(String memberName);
	public void setChief(boolean b);
	public boolean isChief();
	
}
