package com.dexels.navajo.resource.swift;

import java.util.Map;

import com.dexels.navajo.document.types.Binary;

public interface OpenstackStore {
	public void set(String name, Binary contents);
	public Binary get(String name);
	public void delete(String name);
	public Map<String, String> metadata(String name);

}

