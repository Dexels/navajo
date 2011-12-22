package com.dexels.navajo.resource;

import java.util.Map;

 // split in layers
public interface ResourceInstance {

	public void instantiate(ResourceConfig conf, Map<String, Object> settings) throws Exception;
	public ResourceConfig getConfig();
	public void close();
	public Object getSource();
}
