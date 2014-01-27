package com.dexels.navajo.resource;

import java.util.Map;

public interface ResourceFactory {
	public String getType();
	public void instantiate(String id, Map<String,Object> settings);
	public Class<?> getServiceType();
	public void destroy();
}
