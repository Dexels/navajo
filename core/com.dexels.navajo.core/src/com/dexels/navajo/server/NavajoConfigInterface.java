/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public interface NavajoConfigInterface extends NavajoIOConfig {

	public static final int MAX_ACCESS_SET_SIZE = 50;

	// Read/write configuration.

	// Identity methods.
	public String getInstanceName();
	public String getInstanceGroup();
	
	// Available modules.
	public SharedStoreInterface getSharedStore();
	public StatisticsRunnerInterface getStatisticsRunner();
	public ClassLoader getClassloader();
	
	public AsyncStore getAsyncStore();
	public DescriptionProviderInterface getDescriptionProvider();
	public WorkerInterface getIntegrityWorker();
	
	// Statistics.
	public double getCurrentCPUload();
	
	 public boolean useLegacyDateMode();

   
	// Setters/getters.
	public void setStatisticsRunnerEnabled(boolean b);
	public int getMaxAccessSetSize();
	public float getAsyncTimeout();
	public void doClearCache();
	public void doClearScriptCache();
	public boolean isLockManagerEnabled();

	/**
	 * This one consults the configuration
	 * @return
	 */
	public boolean isEnableStatisticsRunner();
	/**
	 * This one asks the statisticsrunner
	 * @return
	 */
	public boolean isStatisticsRunnerEnabled();
	public boolean isCompileScripts();
	

	public Object getParameter(String string);
	
}
