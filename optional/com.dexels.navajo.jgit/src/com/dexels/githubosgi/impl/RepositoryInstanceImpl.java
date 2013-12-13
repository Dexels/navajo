package com.dexels.githubosgi.impl;

import java.io.File;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.RepositoryManager;
import com.dexels.navajo.server.api.NavajoServerContext;

public class RepositoryInstanceImpl implements RepositoryInstance,NavajoServerContext {
	
	private String applicationName;
	protected File applicationFolder;
	protected RepositoryManager repositoryManager;

	@Override
	public File getRepositoryFolder() {
		return this.applicationFolder;
	}

	public void setRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}

	public void clearRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = null;
	}


	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationStatus#getApplicationName()
	 */
	@Override
	public String getRepositoryName() {
		return applicationName;
	}



	@Override
	public int compareTo(RepositoryInstance o) {
		return getRepositoryName().compareTo(o.getRepositoryName());
	}

	@Override
	public String getInstallationPath() {
		return getRepositoryFolder().getAbsolutePath();
	}



}
