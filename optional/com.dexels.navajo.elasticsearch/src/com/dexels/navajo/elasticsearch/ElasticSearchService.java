package com.dexels.navajo.elasticsearch;

import java.io.IOException;

import com.dexels.navajo.document.Message;

public interface ElasticSearchService {
	public void insert(Message m) throws IOException;
	public void insertJson(String jsonString) throws IOException;
}
