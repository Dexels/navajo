package com.dexels.navajo.server.enterprise.statistics;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;

public class DummyStatisticsRunner implements StatisticsRunnerInterface {

	public boolean isEnabled() {
		return false;
	}

	public void setEnabled(boolean b) {
		
	}

	public void addAccess(Access a, Exception e, AsyncMappable am) {
		
	}

	public void onNavajoEvent(NavajoEvent ne) {
		// TODO Auto-generated method stub
		
	}

}
