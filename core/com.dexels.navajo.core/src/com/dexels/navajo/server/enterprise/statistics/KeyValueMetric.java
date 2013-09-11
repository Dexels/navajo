package com.dexels.navajo.server.enterprise.statistics;

public class KeyValueMetric {

	private String key;
	private String value;
	
	public KeyValueMetric(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
}
