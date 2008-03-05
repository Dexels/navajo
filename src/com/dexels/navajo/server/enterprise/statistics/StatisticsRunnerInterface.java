package com.dexels.navajo.server.enterprise.statistics;

import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;

public interface StatisticsRunnerInterface extends NavajoListener {

	public void setEnabled(boolean b);
	public boolean isEnabled();
	public void addAccess(final Access a, final Exception e, AsyncMappable am);
}
