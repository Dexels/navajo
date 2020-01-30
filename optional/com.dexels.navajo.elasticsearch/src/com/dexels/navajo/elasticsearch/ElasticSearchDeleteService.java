package com.dexels.navajo.elasticsearch;

import java.io.IOException;

public interface ElasticSearchDeleteService {
	public void delete(String id) throws IOException;
}
