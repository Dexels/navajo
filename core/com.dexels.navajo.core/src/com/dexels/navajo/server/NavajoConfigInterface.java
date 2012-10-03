package com.dexels.navajo.server;

import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.server.monitoring.ServiceMonitor;

public interface NavajoConfigInterface extends NavajoIOConfig, ServiceMonitor {

	public static final int MAX_ACCESS_SET_SIZE = 50;

	// Read/write configuration.

	// Identity methods.
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
	
	 
	// Webservice, user monitoring options
//	public boolean needsFullAccessLog(Access a);
//	public void setMonitorOn(boolean b);
//	public boolean isMonitorOn();
//	public int getMonitorExceedTotaltime();
//	public String getMonitorUsers();
//	public String getMonitorWebservices();
//	public void setMonitorWebservices(String monitorWebservices);
//	public void setMonitorUsers(String monitorUsers);
//	public void setMonitorExceedTotaltime(int monitorExceedTotaltime);
//	
	
   
	// Setters/getters.
	public void setStatisticsRunnerEnabled(boolean b);

//	public HashMap<String,String> getProperties();
	public String getBetaUser();
	public int getMaxAccessSetSize();
	public float getAsyncTimeout();
	public void doClearCache();
	public void doClearScriptCache();
	public String getCompilationLanguage();

	public boolean isAsyncEnabled();
	public boolean isIntegrityWorkerEnabled();
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
	
	// Start modules.
//	public Message getMessage(String msg);
	public void startTaskRunner();
	public void startStatisticsRunner();
	public Object getParameter(String string);
	
}
