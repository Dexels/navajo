package com.dexels.navajo.server.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.GlobalManager;
import com.dexels.navajo.server.SimpleRepository;

public class GlobalManagerImpl implements GlobalManager {

	private final Map<String,String> settings = new HashMap<String, String>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(GlobalManagerImpl.class);
	
	
	@Override
	public void initGlobals(String method, String username, Navajo inMessage,
			Map<String, String> extraParams) throws NavajoException {
		SimpleRepository.parseBundle(method, username, inMessage, extraParams, settings);
	}

	public void activate(Map<String,Object> settings) {
		for (Entry<String,Object> e : settings.entrySet()) {
			this.settings.put(e.getKey(), ""+e.getValue());
		}
		
	}

	public void deactivate() {
		this.settings.clear();
	}

	@Override
	public void initGlobals(Navajo inMessage) throws NavajoException {
		Header h = inMessage.getHeader();
		if(h==null) {
			logger.warn("Can not append globals to input message: No header found.");
			return;
		}
		String rpcName = h.getRPCName();
		String username = h.getRPCUser();
		initGlobals(rpcName, username, inMessage, settings);
		
	}


}
