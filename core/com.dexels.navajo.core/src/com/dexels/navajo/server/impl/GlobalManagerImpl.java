package com.dexels.navajo.server.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.GlobalManager;
import com.dexels.navajo.server.SimpleRepository;

public class GlobalManagerImpl implements GlobalManager {

	private final Map<String,String> settings = new HashMap<String, String>();
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

}
