package com.dexels.navajo.server.api.impl;

import javax.servlet.ServletContext;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoServerInstance implements NavajoServerContext {

	private final String installationPath;
	private final DispatcherInterface dispatcher;
	private final ServletContext servletContext;

	public NavajoServerInstance(String installationPath,DispatcherInterface dispatcher, ServletContext servletContext) {
		this.installationPath = installationPath;
		this.dispatcher = dispatcher;
		this.servletContext = servletContext;
	}
	@Override
	public DispatcherInterface getDispatcher() {
		return dispatcher;
	}

	@Override
	public String getInstallationPath() {
		return installationPath;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

}
