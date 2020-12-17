/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestBean implements Mappable {

	public String lastname;
	public String firstname;
	public Date dateOfBirth = new java.util.Date();
	public boolean isVip = false;
	public int num;
	public double dub;
	public float flt;
	
	private static final Logger logger = LoggerFactory
			.getLogger(TestBean.class);
	
	
	public String getLastname() {
		return lastname;
	}
	
	public SubBean [] getEmptyBeans() {
		return null;
	}
	
	public SubBean getEmptyBean() {
		return null;
	}
	
	public SubBean [] getSubBeans() {
		SubBean [] beans = new SubBean[2]; 
		beans[0] = new SubBean();
		beans[1] = new SubBean();
		logger.info("GOT " + beans.length + " sub beans");
		return beans;
	}
	
	public List<SubBean> getSubBeanList() {
		ArrayList<SubBean> l = new ArrayList<SubBean>();
		l.add(new SubBean());
		l.add(new SubBean());
		return l;
	}
	
	public SubBean getSubBean() {
		return new SubBean();
	}
	
	public String getAppendedLastname(String noot) {
		return lastname + "/" + noot;
	}
	
	public void setLastname(String lastname) {
		logger.info("In TestBean.setLastName: " + lastname);
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
