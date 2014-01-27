package com.dexels.navajo.server.enterprise.statistics;

import java.util.Map;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;

public class DummyStatisticsRunner implements StatisticsRunnerInterface, NavajoListener {

	private static final DummyStatisticsRunner instance;
	
	static {
		instance = new DummyStatisticsRunner();
		NavajoEventRegistry.getInstance().addListener(AuditLogEvent.class, instance);
	}
	
	public final static DummyStatisticsRunner getInstance() {
		return instance;
	}
	
	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean b) {
		
	}

	@Override
	public void addAccess(Access a, AsyncMappable am) {
		// Do nothing.
	}

	@Override
	public void onNavajoEvent(NavajoEvent ne) {
		if ( ne instanceof AuditLogEvent ) {
			//System.err.println("DummyStatisticsRunner: " + ne);
		}
		ne = null;
	}

	@Override
	public int getAuditLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAuditLevel(int l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(String storePath, Map parameters, String storeClass)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		
	}

}
