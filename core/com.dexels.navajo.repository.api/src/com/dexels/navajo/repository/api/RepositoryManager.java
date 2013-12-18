package com.dexels.navajo.repository.api;


import java.io.File;

public interface RepositoryManager {

	public File getConfigurationFolder();

	public File getRepositoryFolder();

	public File getSshFolder();

}