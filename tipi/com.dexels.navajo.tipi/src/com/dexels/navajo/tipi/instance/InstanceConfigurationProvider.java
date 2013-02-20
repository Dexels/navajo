package com.dexels.navajo.tipi.instance;

import java.util.Set;

public interface InstanceConfigurationProvider {
	public Set<String> getProfiles();
	public Set<String> getDeployments();
}
