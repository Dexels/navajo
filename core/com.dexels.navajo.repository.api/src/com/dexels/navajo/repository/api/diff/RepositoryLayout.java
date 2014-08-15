package com.dexels.navajo.repository.api.diff;

import java.util.List;

public interface RepositoryLayout {
	public List<String> getMonitoredFolders();

	public List<String> getConfigurationFolders();

}
