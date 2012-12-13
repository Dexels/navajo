package com.dexels.navajo.enterprise.scheduler.tribe;

import java.util.HashSet;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerRegistryInterface;
import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class BeforeWebServiceRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5504268916878141151L;

	
	private String webservice;
	private Access myAccess;
	private HashSet<String> ignoreTaskIds;


	private final WebserviceListenerRegistryInterface listenerRegistry;
	
	public BeforeWebServiceRequest(WebserviceListenerRegistryInterface listenerRegistry, String s, Access a, HashSet<String> ignoreList) {
		this.webservice = s;
		this.myAccess = a;
		this.ignoreTaskIds = ignoreList;
		this.listenerRegistry = listenerRegistry;
	}
	
	@Override
	public Answer getAnswer() {
		return new BeforeWebServiceAnswer(listenerRegistry, this);
	}

	public String getWebservice() {
		return webservice;
	}

	public Access getMyAccess() {
		return myAccess;
	}

	public HashSet<String> getIgnoreTaskIds() {
		return ignoreTaskIds;
	}

}
