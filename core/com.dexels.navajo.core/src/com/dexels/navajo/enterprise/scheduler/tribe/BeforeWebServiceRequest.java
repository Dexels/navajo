package com.dexels.navajo.enterprise.scheduler.tribe;

import java.util.Set;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class BeforeWebServiceRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5504268916878141151L;

	
	private String webservice;
	private Access myAccess;
	private Set<String> ignoreTaskIds;
	
	public BeforeWebServiceRequest(String s, Access a, Set<String> ignoreList) {
		this.webservice = s;
		this.myAccess = a;
		this.ignoreTaskIds = ignoreList;
	}
	
	@Override
	public Answer getAnswer() {
		return new BeforeWebServiceAnswer(this);
	}

	public String getWebservice() {
		return webservice;
	}

	public Access getMyAccess() {
		return myAccess;
	}

	public Set<String> getIgnoreTaskIds() {
		return ignoreTaskIds;
	}

}
