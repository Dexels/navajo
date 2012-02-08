package com.dexels.navajo.server.api;

import com.dexels.navajo.server.DispatcherInterface;

public interface NavajoServerContext {

	public DispatcherInterface getDispatcher();
	public String getInstallationPath();
}