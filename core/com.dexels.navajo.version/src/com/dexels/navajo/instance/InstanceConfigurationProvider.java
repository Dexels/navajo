package com.dexels.navajo.instance;

import java.util.Set;

public interface InstanceConfigurationProvider {
	public Set<String> getProfiles();
	public Set<String> getDeployments();
}
