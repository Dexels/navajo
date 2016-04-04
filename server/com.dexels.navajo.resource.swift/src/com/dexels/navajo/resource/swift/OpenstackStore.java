package com.dexels.navajo.resource.swift;

import java.util.Map;

import com.dexels.navajo.document.types.Binary;

public interface OpenstackStore {
	public Binary get(String name);
	public void delete(String name);
	public Map<String, Object> metadata(String name);
	public void set(String name, Binary contents, Map<String, String> metadata);
	
}

