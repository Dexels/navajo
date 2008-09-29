package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;

public interface NavajoConfigInterface {

	// Read/write configuration.
	public Navajo readConfig(String s) throws IOException;
	public void writeConfig(String name, Navajo conf) throws IOException;
	public String getConfigPath();
	public String getRootPath();
	
	// Indentity methods.
	public String getInstanceName();
	public String getInstanceGroup();
	
	// Available modules.
	public PersistenceManager getPersistenceManager();
	public void setRepository(Repository newRepository);
	public Repository getRepository();
	public StatisticsRunnerInterface getStatisticsRunner();
	public NavajoClassSupplier getClassloader();
	public NavajoClassLoader getBetaClassLoader();
	public void setClassloader(NavajoClassSupplier classloader);
	public AsyncStore getAsyncStore();
	public DescriptionProviderInterface getDescriptionProvider();
	public LockManager getLockManager();
	public WorkerInterface getIntegrityWorker();
	
	// Statistics.
	public double getCurrentCPUload();
	
	public boolean needsFullAccessLog(Access a);
	public InputStream getScript(String name) throws IOException;
	public InputStream getConfig(String name) throws IOException;
	 
	// Webservice, user monitoring options
	public void setMonitorOn(boolean b);
	public boolean isMonitorOn();
	public int getMonitorExceedTotaltime();
	public String getMonitorUsers();
	public String getMonitorWebservices();
	public void setMonitorWebservices(String monitorWebservices);
	public void setMonitorUsers(String monitorUsers);
	public void setMonitorExceedTotaltime(int monitorExceedTotaltime);
	
	// Setters/getters.
	public void setStatisticsRunnerEnabled(boolean b);
	public String getAdapterPath();
	public String getClassPath();
	public File getJarFolder();
	public String getScriptPath();
	public String getResourcePath();
	public HashMap<String,String> getProperties();
	public String getBetaUser();
	public String getCompiledScriptPath();
	public int getMaxAccessSetSize();
	public float getAsyncTimeout();
	public String getDbPath();
	public void doClearCache();
	public void doClearScriptCache();
	
	public boolean isAsyncEnabled();
	public boolean isIntegrityWorkerEnabled();
	public boolean isLockManagerEnabled();
	public boolean isEnableStatisticsRunner();
	public boolean isStatisticsRunnerEnabled();
	public boolean isCompileScripts();
	
	// Start modules.
	public void startJabber();
	public void startTaskRunner();
	public void startStatisticsRunner();
	
}
