package com.dexels.navajo.resource;

import java.util.List;
import java.util.Map;

// TODO use generics?
public interface ResourceConfig {
	// jdbc, mongo, navajo, http, ftp, pop, smtp, jcr
	// for instances (all resourceinstances share this)
	public static final String NAME = "name";

	// for service reg:
	public static final String TYPE = "type";
	public static final String CONFIGNAME = "configname";

	
	public List<String> accepts();
	public List<String> requires();
	public ResourceInstance createInstance(Map<String, Object> settings)
			throws Exception;
	public String getType();
	public String getConfigName();
}
