package com.dexels.navajo.elasticsearch.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.elasticsearch.ElasticSearch;
import com.dexels.navajo.elasticsearch.ElasticSearchFactory;
import com.dexels.navajo.elasticsearch.FscrawlerFactory;
import com.dexels.navajo.elasticsearch.FscrawlerService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FscrawlerComponent implements FscrawlerService{
private final static Logger logger = LoggerFactory.getLogger(FscrawlerComponent.class);
	
	private CloseableHttpClient httpclient;

	private String url;

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public void activate(Map<String,Object> settings) {
	
		logger.info("Activating...");
		httpclient = HttpClients.createDefault();
		this.url = (String)settings.get("url");
		FscrawlerFactory.setInstance(this);
	}
	
	public void deactivate() {
		logger.info("Deactivating...");
		FscrawlerFactory.setInstance(null);
		if(httpclient!=null) {
			try {
				System.out.println(">>>>>Connection Closed (in deactivate)");
				httpclient.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}

	}
	public void upload(Binary data, String id, String name) throws IOException {
		//String url = "http://127.0.0.1:8080/fscrawler/_upload";
		 	
		logger.info("IN UPLOAD: " + name);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, data.getDataAsStream());
		
		//Binary b;
		//b.guessContentType();
		
	    HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", new ByteArrayBody(baos.toByteArray(), ContentType.TEXT_PLAIN,name))
                .addPart("id", new StringBody(id,ContentType.TEXT_PLAIN))
                .build();

	    HttpPost request = new HttpPost(url);
	    	request.setEntity(entity);
	    	
	    //trial code will be deleted in today 28 Jan 2020
	    System.out.println("Entity: " + entity.toString());
	    System.out.println("//========================================//");
	    System.out.println("Request: " + request.toString());
	    //=======================//

	    HttpClient client = HttpClientBuilder.create().build();
	    HttpResponse response = client.execute(request);
		
		
		
		System.out.println(response.getStatusLine());
		
		System.out.println(">>>>>Connection Closed (under status line)");
		httpclient.close();
		
	}
	
	private static void copyResource(OutputStream out, InputStream in) throws IOException {
		try(BufferedInputStream bin = new BufferedInputStream(in); BufferedOutputStream bout = new BufferedOutputStream(out);) {
			byte[] buffer = new byte[1024];
			int read = -1;
			boolean ready = false;
			while (!ready) {

				read = bin.read(buffer);
				System.err.println("data: "+read);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}

				if (read <= -1) {
					ready = true;
				}
			}
	}
	}	
	
	
	
}
