/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
