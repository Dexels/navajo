package com.dexels.navajo.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;

import com.dexels.navajo.elasticsearch.impl.ElasticSearchResult;


public interface ElasticSearchQueryService {
	public ElasticSearchResult[] search(String keyword) throws IOException;
}
