package com.dexels.navajo.resource.navajo.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.document.Navajo;

public class NavajoComponentImpl implements LocalClient  {

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoComponentImpl.class);
	
	public void activate(Map<String, String> settings) {
		logger.debug("Activating HTTP connector with: " + settings);
		for (Entry<String, String> e : settings.entrySet()) {
			logger.debug("key: " + e.getKey() + " value: " + e.getValue());
		}
		
	}

	
	public void deactivate() {
		logger.debug("Deactivating HTTP connector");
	}


	@Override
	public Navajo call(Navajo n) throws FatalException {
		return null;
	}


	@Override
	public Navajo generateAbortMessage(String reason) throws FatalException {
		return null;
	}


	@Override
	public Navajo handleCallback(Navajo n, String callback) {
		return null;
	}


	@Override
	public Navajo handleInternal(Navajo in, Object cert, ClientInfo clientInfo)
			throws FatalException {
		return null;
	}


	@Override
	public boolean isSpecialWebservice(String name) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getApplicationId() {
		// TODO Auto-generated method stub
		return null;
	}

}
