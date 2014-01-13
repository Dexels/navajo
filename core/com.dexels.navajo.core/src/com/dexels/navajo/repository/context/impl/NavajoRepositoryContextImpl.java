package com.dexels.navajo.repository.context.impl;

import java.io.IOException;

import org.osgi.framework.InvalidSyntaxException;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.server.api.NavajoServerContext;
import com.dexels.navajo.server.impl.NavajoServerContextComponent;

public class NavajoRepositoryContextImpl extends NavajoServerContextComponent implements NavajoServerContext {
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
	
	public void activate() throws IOException, InvalidSyntaxException {
		initializeContext(getInstallationPath());
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
	}

}
