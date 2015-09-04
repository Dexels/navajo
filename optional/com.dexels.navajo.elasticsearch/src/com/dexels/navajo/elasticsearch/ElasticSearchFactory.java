package com.dexels.navajo.elasticsearch;

public class ElasticSearchFactory {
	
	private static ElasticSearchService instance = null;

	public static void setInstance(ElasticSearchService service) {
		instance = service;
	}
	
	public static ElasticSearchService getInstance() {
		return instance;
	}
}
