/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.global.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.server.global.GlobalManager;
import com.dexels.navajo.server.global.GlobalManagerRepository;
import com.dexels.navajo.server.global.GlobalManagerRepositoryFactory;

public class GlobalManagerRepositoryImpl implements GlobalManagerRepository {
	private final Map<String,GlobalManager> globalManagers = new HashMap<String, GlobalManager>();
	private GlobalManager defaultGlobalManager = null;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.global.impl.GlobalManagerRepository#addGlobalManager(java.lang.String, com.dexels.navajo.server.GlobalManager)
	 */
	@Override
	public void addGlobalManager(GlobalManager manager, Map<String,Object> settings) {
		String instance = (String) settings.get("instance");
		if(instance==null) {
			defaultGlobalManager  = manager;
		} else {
			globalManagers.put(instance, manager);
			
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.global.impl.GlobalManagerRepository#removeGlobalManager(java.lang.String, com.dexels.navajo.server.GlobalManager)
	 */
	@Override
	public void removeGlobalManager(GlobalManager manager,Map<String,Object> settings) {
		String instance = (String) settings.get("instance");
		if(instance==null) {
			defaultGlobalManager  = null;
		} else {
			globalManagers.remove(instance);
			
		}
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

	@Override
	public GlobalManager getDefaultGlobalManager() {
		return defaultGlobalManager;
	}

}
