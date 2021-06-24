/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.global;

import java.util.Map;

public interface GlobalManagerRepository {

	public void addGlobalManager(GlobalManager manager, Map<String,Object> settings);

	public void removeGlobalManager(GlobalManager manager,Map<String,Object> settings);

	public GlobalManager getGlobalManager(String instance);

	public GlobalManager getDefaultGlobalManager();

}