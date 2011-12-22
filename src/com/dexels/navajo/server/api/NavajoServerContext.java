package com.dexels.navajo.server.api;

import javax.servlet.ServletContext;

import com.dexels.navajo.server.DispatcherInterface;

public interface NavajoServerContext {

	public DispatcherInterface getDispatcher();
	public String getInstallationPath();
	public ServletContext getServletContext();
}