package com.dexels.navajo.elasticsearch.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.elasticsearch.ElasticSearchDeleteFactory;
import com.dexels.navajo.elasticsearch.ElasticSearchDeleteService;

public class ElasticSearchDeleteComponent implements ElasticSearchDeleteService {
	private final static Logger logger = LoggerFactory.getLogger(ElasticSearchDeleteComponent.class);
	private String main_url = "http://es-tucht-test.test.svc.cluster.local:9200/job_10/_doc/";
	private CloseableHttpClient httpclient;
	private ElasticSearchDeleteResult result;
	
	public void activate(Map<String,Object> settings) {
		logger.info("Activating...");
		httpclient = HttpClients.createDefault();
		ElasticSearchDeleteFactory.setInstance(this);
	}
	
	public void deactivate() {
		logger.info("Deactivating...");
		ElasticSearchDeleteFactory.setInstance(null);
	}

	@Override
	public ElasticSearchDeleteResult delete(String id) throws IOException {
		// TODO Auto-generated method stub
        String full_url = main_url + id;

        System.out.println("FULL URL : " + full_url);

        HttpDelete request = new HttpDelete(full_url);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);


        
        System.out.println(">>>>> Response status: " + response.getStatusLine());
              

        httpclient.close();
        System.out.println(">>>>>Connection Closed");
        
        
		
		result = new ElasticSearchDeleteResult(id, response.getStatusLine().toString().replace("HTTP/1.1 ", ""));
		
		
		
		
		
		return result; 
		
	}

}
