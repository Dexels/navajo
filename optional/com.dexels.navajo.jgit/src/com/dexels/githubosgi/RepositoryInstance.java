package com.dexels.githubosgi;

import java.io.File;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"settingsBundle","applicationManager"})
public interface RepositoryInstance extends Comparable<RepositoryInstance> {
	
	public File getAppFolder();

	public String getApplicationName();


}