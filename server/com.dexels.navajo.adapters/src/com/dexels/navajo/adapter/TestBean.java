/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import java.util.Date;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestBean implements Mappable {

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

	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		
	}

	@Override
	public void kill() {
		
	}
	
}
