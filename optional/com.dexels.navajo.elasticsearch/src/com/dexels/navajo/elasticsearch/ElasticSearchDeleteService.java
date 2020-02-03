package com.dexels.navajo.elasticsearch;

import java.io.IOException;

import com.dexels.navajo.elasticsearch.impl.ElasticSearchDeleteResult;

public interface ElasticSearchDeleteService {
	public ElasticSearchDeleteResult delete(String id) throws IOException;
}
