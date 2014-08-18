package com.dexels.navajo.repository.api.diff.impl;

import java.util.Arrays;
import java.util.List;

import com.dexels.navajo.repository.api.diff.RepositoryLayout;

public class NavajoMultiTenantRepositoryLayout implements RepositoryLayout {
//
//	result.add("scripts");
//	result.add("article");
//	result.add("authorization");
//	result.add("adapters");
//	result.add("camel");
//	result.add("workflows");
//	result.add("tasks");
	
	private final List<String> monitoredFolders = Arrays.asList("config", "scripts", "article","authorization","adapters","features","workflows","tasks");
	private final List<String> configurationFolders = Arrays.asList("config","adapters","features");

	@Override
	public List<String> getMonitoredFolders() {
		return monitoredFolders;
	}
	@Override
	public List<String> getConfigurationFolders() {
		return configurationFolders;
	}

}
