package com.dexels.navajo.server.global;

import java.util.Map;

public interface GlobalManagerRepository {

	public void addGlobalManager(GlobalManager manager, Map<String,Object> settings);

	public void removeGlobalManager(GlobalManager manager,Map<String,Object> settings);

	public GlobalManager getGlobalManager(String instance);

	public GlobalManager getDefaultGlobalManager();

}