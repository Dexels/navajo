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
	public void initialize(String storePath, Map parameters, String storeClass)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException;
	
}
