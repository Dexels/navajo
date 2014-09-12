package com.dexels.navajo.repository.api.diff.impl;

import java.util.Arrays;
import java.util.List;

import com.dexels.navajo.repository.api.diff.RepositoryLayout;

public class TipiRepositoryLayout implements RepositoryLayout {

	private final List<String> monitoredFolders = Arrays.asList("tipi", "resource", "settings");
	private final List<String> configurationFolders = Arrays.asList("features");

	@Override
	public List<String> getMonitoredFolders() {
		return monitoredFolders;
	}

	@Override
	public List<String> getConfigurationFolders() {
		return configurationFolders;
	}
}
