package com.dexels.navajo.repository.api.diff.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dexels.navajo.repository.api.diff.RepositoryLayout;

public class GenericRepositoryLayout implements RepositoryLayout {

	private final List<String> monitoredFolders = Collections.emptyList();
	private final List<String> configurationFolders = Arrays.asList("etc");

	@Override
	public List<String> getMonitoredFolders() {
		return monitoredFolders;
	}

	@Override
	public List<String> getConfigurationFolders() {
		return configurationFolders;
	}
}
