/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	
	/** 
     * Indicates whether all data is properly migrated and backup up amongst the members from this member
     */
    public boolean isSafe();
	
}
