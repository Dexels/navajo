package com.dexels.navajo.tipi.vaadin.instance;

import java.util.Set;

@Deprecated
public interface InstanceConfigurationProvider {
	public Set<String> getProfiles();
	public Set<String> getDeployments();
}
