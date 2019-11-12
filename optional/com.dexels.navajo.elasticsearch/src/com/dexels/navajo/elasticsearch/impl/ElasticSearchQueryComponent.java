package com.dexels.navajo.elasticsearch.impl;

import java.io.IOException;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.elasticsearch.ElasticSearchQueryFactory;
import com.dexels.navajo.elasticsearch.ElasticSearchQueryService;
import com.dexels.navajo.elasticsearch.FscrawlerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;





public class ElasticSearchQueryComponent implements ElasticSearchQueryService {
private final static Logger logger = LoggerFactory.getLogger(FscrawlerComponent.class);
	
	private CloseableHttpClient httpclient;
	
	public String url;

	
	public void activate(Map<String,Object> settings) {
	
		logger.info("Activating...");
		httpclient = HttpClients.createDefault();
		this.url = (String)settings.get("url");
		ElasticSearchQueryFactory.setInstance(this);
	}
	
	public void deactivate() {
		logger.info("Deactivating...");
		ElasticSearchQueryFactory.setInstance(null);
		if(httpclient!=null) {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		

	}

	@Override
	public ElasticSearchResult[] search(String keyword) throws IOException {
		ArrayList<ElasticSearchResult> es_result = new ArrayList<ElasticSearchResult>();
		String result = null;

		//curl -XGET "http://localhost:9200/job_10/_search?q=content:dokimastiko OR content:txt"
		//if we want to search exact phrase into a document or one or more fields we can use OR, AND etc
		
		System.out.println("the keyword is: " + keyword);
		
		String es_url = this.url + URLEncoder.encode(keyword, "UTF-8");;
		
			
		HttpGet request = new HttpGet(es_url);
		
		request.addHeader("Content-Type", "application/json");
		
		CloseableHttpResponse response = httpclient.execute(request);

		
		
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
           // return it as a String
           result = EntityUtils.toString(entity);
           System.out.println("ENTITY: "+result);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode n = mapper.readTree(result);
		JsonNode json_array = n.get("hits").get("hits");
		
		for(JsonNode jsonNode : json_array) {
			String id = jsonNode.get("_id").toString();
			String score = jsonNode.get("_score").toString();
			String fileName = jsonNode.get("_source").get("file").get("filename").toString();
			ElasticSearchResult esr = new ElasticSearchResult(id, score, fileName);
			es_result.add(esr);
		}
		

		ElasticSearchResult[] es_result_array = es_result.toArray(new ElasticSearchResult[es_result.size()]);
	
		response.close();
		
		return es_result_array;
	}
}
