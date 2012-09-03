package com.dexels.navajo.resource.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.http.HttpResource;

public class ResourceComponent implements HttpResource {

	private final static Logger logger = LoggerFactory
			.getLogger(ResourceComponent.class);
	private String url;
	private ExecutorService threadPool = null;
	
	public void activate(Map<String, String> settings) {
		logger.info("Activating HTTP connector with: " + settings);
		for (Entry<String, String> e : settings.entrySet()) {
			logger.info("key: " + e.getKey() + " value: " + e.getValue());
		}
		this.url = settings.get("url");
//		threadPool = Executors.newFixedThreadPool
		
	}

	public void modified(Map<String,String> settings) {
		logger.info("Modifying HTTP connector with: " + settings);
	}
	
	public void deactivate() {
		System.err.println("Deactivating HTTP connector");
	}

	@Override
	public Future<InputStream> callAsync() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream call() throws IOException {
		URL u = new URL(url);
		InputStream is = u.openStream();
		return is;
	}

	@Override
	public String getURL() {
		return url;
	}
}
