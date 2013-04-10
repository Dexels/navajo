package com.dexels.navajo.resource.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.http.HttpResource;

public class ResourceComponent implements HttpResource {

	private final static Logger logger = LoggerFactory
			.getLogger(ResourceComponent.class);
	private String url;
//	private ExecutorService threadPool = null;
	
	public void activate(Map<String, String> settings) {
		logger.debug("Activating HTTP connector with: " + settings);
		for (Entry<String, String> e : settings.entrySet()) {
			logger.debug("key: " + e.getKey() + " value: " + e.getValue());
		}
		this.url = settings.get("url");
//		threadPool = Executors.newFixedThreadPool
		
	}

	public void modified(Map<String,String> settings) {
		logger.debug("Modifying HTTP connector with: " + settings);
	}
	
	public void deactivate() {
		logger.debug("Deactivating HTTP connector");
	}

	@Override
	public Future<InputStream> callAsync() {
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

	@Override
	public InputStream call(OutputStream os) throws MalformedURLException,
			IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<InputStream> callAsync(OutputStream os) {
		// TODO Auto-generated method stub
		return null;
	}
}
