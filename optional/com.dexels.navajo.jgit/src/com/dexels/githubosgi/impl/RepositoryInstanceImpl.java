package com.dexels.githubosgi.impl;

import java.io.File;

import com.dexels.githubosgi.RepositoryInstance;
import com.dexels.githubosgi.RepositoryManager;

public class RepositoryInstanceImpl implements RepositoryInstance {
	
	private String applicationName;
	protected File applicationFolder;
	protected RepositoryManager repositoryManager;

	@Override
	public File getAppFolder() {
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
	public String getApplicationName() {
		return applicationName;
	}



	@Override
	public int compareTo(RepositoryInstance o) {
		return getApplicationName().compareTo(o.getApplicationName());
	}



}
