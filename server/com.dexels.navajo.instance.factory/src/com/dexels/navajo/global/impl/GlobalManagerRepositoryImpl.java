package com.dexels.navajo.global.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.server.global.GlobalManager;
import com.dexels.navajo.server.global.GlobalManagerRepository;
import com.dexels.navajo.server.global.GlobalManagerRepositoryFactory;

public class GlobalManagerRepositoryImpl implements GlobalManagerRepository {
	private final Map<String,GlobalManager> globalManagers = new HashMap<String, GlobalManager>();
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.global.impl.GlobalManagerRepository#addGlobalManager(java.lang.String, com.dexels.navajo.server.GlobalManager)
	 */
	@Override
	public void addGlobalManager(GlobalManager manager, Map<String,Object> settings) {
		String instance = (String) settings.get("instance");
		globalManagers.put(instance, manager);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.global.impl.GlobalManagerRepository#removeGlobalManager(java.lang.String, com.dexels.navajo.server.GlobalManager)
	 */
	@Override
	public void removeGlobalManager(GlobalManager manager,Map<String,Object> settings) {
		String instance = (String) settings.get("instance");
		globalManagers.remove(instance);
	}

	public void activate() {
		GlobalManagerRepositoryFactory.setGlobalManagerInstance(this);
	}

	public void deactivate() {
		GlobalManagerRepositoryFactory.setGlobalManagerInstance(null);
	}

	@Override
	public GlobalManager getGlobalManager(String instance) {
		return globalManagers.get(instance);
	}

}
