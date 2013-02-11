package com.dexels.navajo.tipi.vaadin.instance;

import java.util.Set;

public interface InstanceConfigurationProvider {
	public Set<String> getProfiles();
	public Set<String> getDeployments();
}
