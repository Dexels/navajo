package com.dexels.navajo.elasticsearch.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.impl.client.HttpClients;

import com.dexels.navajo.elasticsearch.ElasticSearchDeleteService;
import com.dexels.navajo.elasticsearch.ElasticSearchQueryFactory;

public class ElasticSearchDeleteComponent implements ElasticSearchDeleteService {
	
	
	
	
	
	
	public void activate(Map<String,Object> settings) {
		
	}
	
	public void deactivate() {

	}
	
	

	@Override
	public String delete_id(String id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
