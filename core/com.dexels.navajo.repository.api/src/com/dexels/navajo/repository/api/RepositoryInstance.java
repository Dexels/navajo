package com.dexels.navajo.repository.api;


import java.io.File;
import java.util.List;
import java.util.Map;

public interface RepositoryInstance extends Comparable<RepositoryInstance> {
	
	public File getRepositoryFolder();

	public String getRepositoryName();

	public Map<String, Object> getSettings();

	public void addOperation(AppStoreOperation op, Map<String, Object> settings);

	public void removeOperation(AppStoreOperation op, Map<String, Object> settings);

	public List<String> getOperations();

}