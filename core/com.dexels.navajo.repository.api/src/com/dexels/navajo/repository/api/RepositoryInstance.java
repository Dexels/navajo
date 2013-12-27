package com.dexels.navajo.repository.api;


import java.io.File;
import java.util.Map;

public interface RepositoryInstance extends Comparable<RepositoryInstance> {
	
	public File getRepositoryFolder();

	public String getRepositoryName();

	public Map<String, Object> getSettings();


}