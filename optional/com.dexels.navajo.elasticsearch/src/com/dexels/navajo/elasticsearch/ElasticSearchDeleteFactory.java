package com.dexels.navajo.elasticsearch;

public class ElasticSearchDeleteFactory {
	private static ElasticSearchDeleteService instance = null;
	
	public static void setInstance(ElasticSearchDeleteService service) {
		instance = service;
	}
	
	public static ElasticSearchDeleteService getInstance() {
		return instance;
	}

}
