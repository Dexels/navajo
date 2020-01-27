package com.dexels.navajo.elasticsearch;

import java.io.IOException;



public interface ElasticSearchDeleteService {
	public String delete_id(String id) throws IOException;
}
