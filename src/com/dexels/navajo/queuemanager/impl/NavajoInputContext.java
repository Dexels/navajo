package com.dexels.navajo.queuemanager.impl;

import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.queuemanager.api.InputContext;

public class NavajoInputContext implements InputContext {

	private final Navajo inputNavajo;
	private final HttpServletRequest request;
	public NavajoInputContext(Navajo in, HttpServletRequest request) {
		inputNavajo = in;
		this.request = request;
	}
	@Override
	public Navajo getInputNavajo() {
		return inputNavajo;
	}

	@Override
	public String getServiceName() {
		return getInputNavajo().getHeader().getRPCName();
	}

	@Override
	public String getUserName() {
		return getInputNavajo().getHeader().getRPCUser();
	}
	@Override
	public String getResourceAvailability() {
		// link to ResourceManager
		return "OK";
	}
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

}
