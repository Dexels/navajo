package com.dexels.navajo.enterprise.scheduler.tribe;

import java.util.Set;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class AfterWebServiceRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4545825900595190916L;

	private String webservice;
	private Access myAccess;
	private Set<String> ignoreTaskIds;
	
	public AfterWebServiceRequest(String service, Access a, Set<String> ignoreList) {
		this.webservice = service;
		this.myAccess = a;
		this.ignoreTaskIds = ignoreList;
	}
	
	@Override
	public Answer getAnswer() {
		return new AfterWebServiceAnswer(this);
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
