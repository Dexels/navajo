package com.dexels.navajo.mapping.compiler.meta;

public class AdapterResourceDependency extends AdapterFieldDependency {

	private final String resourceType;

	public AdapterResourceDependency(long timestamp, String adapterClass,
			String type, String id, String resourceType) {
		super(timestamp, adapterClass, type, id);
		this.resourceType = resourceType;
	}

}
