package com.dexels.navajo.resource.group;

public interface ResourceGroup {
	public String getName();

	public String getType();

	public Object getResource(String instance);
}
