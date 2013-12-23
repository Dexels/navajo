package com.dexels.githubosgi.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.RepositoryManager;

public class RepositoryInstanceImpl implements RepositoryInstance {
	
	protected String repositoryName;
	protected File applicationFolder;
	protected RepositoryManager repositoryManager;
	private final Map<String,Object> settings = new HashMap<String, Object>();
	
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


	@Override
	public String getRepositoryName() {
		return repositoryName;
	}



	@Override
	public int compareTo(RepositoryInstance o) {
		return getRepositoryName().compareTo(o.getRepositoryName());
	}

	@Override
	public Map<String,Object> getSettings() {
		return settings;
	}
	
	protected void setSettings(Map<String,Object> settings) {
		settings.clear();
		settings.putAll(settings);
	}

}
