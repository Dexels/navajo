package com.dexels.navajo.server.enterprise.statistics;

import java.util.Map;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.NavajoConfigInterface;

public class StatisticsRunnerComponent implements StatisticsRunnerInterface {
	/**
	 * @param cc  
	 */
	public void activate() {
		System.out.println("hier");
	}

	public void deactivate() {
		
	}

	/**
	 * @param nvi  
	 */
	public void setNavajoConfig(NavajoConfigInterface nvi) {
		
	}

	/**
	 * @param nvi  
	 */
	public void clearNavajoConfig(NavajoConfigInterface nvi) {
		
	}

	@Override
	public void onNavajoEvent(NavajoEvent ne) {
		
		
	}

	@Override
	public void setEnabled(boolean b) {
		
		
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void addAccess(Access a, AsyncMappable am) {
		
		
	}

	@Override
	public int getAuditLevel() {
		return 0;
	}

	@Override
	public void setAuditLevel(int l) {

	}

	@Override
	public void initialize(String storePath, Map parameters, String storeClass)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
				
	}

}
