/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.test;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.server.FileNavajoConfig;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.sharedstore.SharedFileStore;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class TestNavajoConfig extends FileNavajoConfig implements NavajoConfigInterface {

	String name = "testinstance";
	String group = "testgroup";
	
	private final File configRoot;
	
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(TestNavajoConfig.class);
	
	public TestNavajoConfig() {
		this(null);
	}
	
	public TestNavajoConfig(File configRoot) {
		this.configRoot = configRoot;
	}
	
	public TestNavajoConfig(String name, String group) {
		this.name = name;
		this.group = group;
		this.configRoot = null;
	}
	

	
	@Override
	public String getInstanceName() {
		return name;
	}


	@Override
	public String getConfigPath() {
		return new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
	}

	@Override
	public String getInstanceGroup() {
		return group;
	}

	@Override
	public double getCurrentCPUload() {
		return 0;
	}



	@Override
	public ClassLoader getClassloader() {
		return getClass().getClassLoader();
	}

	@Override
	public StatisticsRunnerInterface getStatisticsRunner() {
		return null;
	}

	@Override
	public String getRootPath() {
		return getConfigPath();
	}

	@Override
	public String getAdapterPath() {
		return null;
	}

	@Override
	public AsyncStore getAsyncStore() {
		return null;
	}

	@Override
	public float getAsyncTimeout() {
		return 0;
	}

	@Override
	public String getCompiledScriptPath() {
		return new File(configRoot,"classes").getAbsolutePath();
	}

	@Override
	public DescriptionProviderInterface getDescriptionProvider() {
		return null;
	}

	@Override
	public String getResourcePath() {
		return null;
	}

	@Override
	public String getScriptPath() {
		return new File(configRoot,"scripts").getAbsolutePath();
	}


	@Override
	public boolean isEnableStatisticsRunner() {
		return false;
	}

	@Override
	public boolean isLockManagerEnabled() {
		return false;
	}

	@Override
	public boolean isStatisticsRunnerEnabled() {
		return false;
	}

	@Override
	public void setStatisticsRunnerEnabled(boolean b) {
	}


	@Override
	public void doClearCache() {
		
		
	}

	@Override
	public boolean isCompileScripts() {
		return false;
	}

	@Override
	public void doClearScriptCache() {
	}

	@Override
	public WorkerInterface getIntegrityWorker() {
		
		return null;
	}

	@Override
	public int getMaxAccessSetSize() {
		return 0;
	}

	@Override
	public File getContextRoot() {
		
		return null;
	}

	@Override
	public Object getParameter(String string) {
		return null;
	}

	@Override
	public SharedStoreInterface getSharedStore() {
		try {
			return new SharedFileStore();
		} catch (Exception e) {
			logger.error("Error: ", e);
			return null;
		}
	}

    @Override
    public boolean useLegacyDateMode() {
        return false;
    }



}
