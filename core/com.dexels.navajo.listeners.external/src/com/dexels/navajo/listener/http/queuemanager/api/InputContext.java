package com.dexels.navajo.listener.http.queuemanager.api;


import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;

public interface InputContext {

	public Navajo getInputNavajo();
	public String getServiceName();
	public String getUserName();
	public String getResourceAvailability();
	
	public HttpServletRequest getRequest();
	boolean isPriority();
}