/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.statistics;

import java.util.Map;

import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;

public interface StatisticsRunnerInterface extends NavajoListener {

	public void setEnabled(boolean b);
	public boolean isEnabled();
	public void addAccess(final Access a, AsyncMappable am);
	public int getAuditLevel();
	public void setAuditLevel(int l);
	
	@SuppressWarnings("rawtypes")
    public void initialize(String storePath, Map parameters, String storeClass) throws  Exception;
	
}
