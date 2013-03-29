package com.dexels.navajo.resource.navajo.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.AsyncClient;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.document.Navajo;

public class NavajoComponentImpl implements AsyncClient  {

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoComponentImpl.class);
	
	private ManualAsyncClient async = null;

	private String name;

//	private String username;
//	private String password;
//	private String server;
//	private String server;
	
	public void activate(Map<String, String> settings) {
		async = AsyncClientFactory.createInstance();
		modify(settings);
	}

	public void modify(Map<String, String> settings) {
		String username = settings.get("username");
		String password = settings.get("password");
		String server = settings.get("server");
		this.name = settings.get("name");
		async.setUsername(username);
		async.setPassword(password);
		async.setUsername(server);
		logger.debug("Configuring navajo connector with: " + settings);
		for (Entry<String, String> e : settings.entrySet()) {
			logger.debug("key: " + e.getKey() + " value: " + e.getValue());
		}
	}	
	public void deactivate() {
		logger.debug("Deactivating HTTP connector");
		async.close();
	}


	@Override
	public void callService(Navajo input, String service,
			NavajoResponseHandler continuation) throws IOException {

		async.callService(input, service, continuation);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Navajo callService(Navajo input, String service) throws IOException {
		return async.callService(input, service);
	}



}
