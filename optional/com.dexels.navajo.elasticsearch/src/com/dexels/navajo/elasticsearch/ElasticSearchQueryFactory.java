package com.dexels.navajo.elasticsearch;

public class ElasticSearchQueryFactory {
	private static ElasticSearchQueryService instance = null;

	public static void setInstance(ElasticSearchQueryService service) {
		instance = service;
	}
	
	public static ElasticSearchQueryService getInstance() {
		return instance;
	}


}
