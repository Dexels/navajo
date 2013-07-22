package com.dexels.navajo.server;

import java.util.Map;

import com.dexels.navajo.server.GlobalManager;

public interface GlobalManagerRepository {

	public void addGlobalManager(GlobalManager manager, Map<String,Object> settings);

	public void removeGlobalManager(GlobalManager manager,Map<String,Object> settings);

	public GlobalManager getGlobalManager(String instance);
}