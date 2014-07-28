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
	
	private final List<String> monitoredFolders = Arrays.asList("config", "scripts", "article","authorization","workflows","tasks", "entities");
	@Override
	public List<String> getMonitoredFolders() {
		String suppressAdapters = System.getenv("navajo.suppress.adaptersfolder");
		if("true".equals(suppressAdapters)) {
			return monitoredFolders;		
		}
		List<String> result = new ArrayList<String>(monitoredFolders);
		result.add("adapters");
		return result;
		
	}

}
