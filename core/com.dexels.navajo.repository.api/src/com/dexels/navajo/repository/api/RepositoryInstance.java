package com.dexels.navajo.repository.api;


import java.io.File;

public interface RepositoryInstance extends Comparable<RepositoryInstance> {
	
	public File getRepositoryFolder();

	public String getRepositoryName();


}