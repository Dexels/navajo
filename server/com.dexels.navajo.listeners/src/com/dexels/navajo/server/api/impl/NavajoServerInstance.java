package com.dexels.navajo.server.api.impl;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;


public class NavajoServerInstance implements NavajoServerContext {

	private final String installationPath;
	private final DispatcherInterface dispatcher;

	public NavajoServerInstance(String installationPath,DispatcherInterface dispatcher) {
		this.installationPath = installationPath;
		this.dispatcher = dispatcher;
	}
	@Override
	public DispatcherInterface getDispatcher() {
		return dispatcher;
	}

	@Override
	public String getInstallationPath() {
		return installationPath;
	}

}
