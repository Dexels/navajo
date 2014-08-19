package com.dexels.navajo.repository.api.diff.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dexels.navajo.repository.api.diff.RepositoryLayout;

public class NavajoRepositoryLayout implements RepositoryLayout {
//
//	result.add("scripts");
//	result.add("article");
//	result.add("authorization");
//	result.add("adapters");
//	result.add("camel");
//	result.add("workflows");
//	result.add("tasks");
	
	private final List<String> monitoredFolders = Arrays.asList("config", "scripts", "article","authorization","workflows","tasks","features");
	private final List<String> configurationFolders = Arrays.asList("config","features");

	@Override
	public List<String> getMonitoredFolders() {
		String suppressAdapters = System.getProperty("navajo.suppress.adaptersfolder");
		if (suppressAdapters == null) {
			suppressAdapters = System.getenv("navajo.suppress.adaptersfolder");
		}		if("true".equals(suppressAdapters)) {
			return monitoredFolders;		
		}
		List<String> result = new ArrayList<String>(monitoredFolders);
		result.add("adapters");
		return result;
		
	}
	
	@Override
	public List<String> getConfigurationFolders() {
		String suppressAdapters = System.getProperty("navajo.suppress.adaptersfolder");
		if (suppressAdapters == null) {
			suppressAdapters = System.getenv("navajo.suppress.adaptersfolder");
		}
		
		if("true".equals(suppressAdapters)) {
			return configurationFolders;		
		}
		List<String> result = new ArrayList<String>(configurationFolders);
		result.add("adapters");
		return result;
	}


}
