package com.dexels.navajo.repository.context.impl;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoRepositoryContextImpl implements NavajoServerContext {
	private RepositoryInstance repositoryInstance;
	
	public void setRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = ri;
	}

	public void clearRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = null;
	}

	@Override
	public String getInstallationPath() {
		return repositoryInstance.getRepositoryFolder().getAbsolutePath();
	}

}
