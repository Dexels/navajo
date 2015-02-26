package com.dexels.navajo.server.test;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.script.api.NavajoClassSupplier;
import com.dexels.navajo.server.FileNavajoConfig;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.SimpleRepository;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.sharedstore.SharedFileStore;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class TestNavajoConfig extends FileNavajoConfig implements NavajoConfigInterface {

	String name = "testinstance";
	String group = "testgroup";
	
	private PersistenceManager myPersistenceManager;
	private NavajoClassSupplier myClassloader;
	private final File configRoot;
	
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestNavajoConfig.class);
	
	public TestNavajoConfig() throws Exception {
		this(null);
	}
	
	public TestNavajoConfig(File configRoot) throws Exception {
		this.configRoot = configRoot;
		setClassloader( new NavajoClassLoader(this.getClass().getClassLoader()));
	}
	
	public TestNavajoConfig(String name, String group) {
		this.name = name;
		this.group = group;
		this.configRoot = null;
	}
	
	
	public void setMyPersistenceManager(PersistenceManager myPersistenceManager) {
		this.myPersistenceManager = myPersistenceManager;
	}

	
	@Override
	public String getInstanceName() {
		return name;
	}

	@Override
	public PersistenceManager getPersistenceManager() {
		return myPersistenceManager;
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
	public Repository getRepository() {
		return new SimpleRepository();
	}


	@Override
	public NavajoClassSupplier getClassloader() {
		return myClassloader;
	}

	@Override
	public void setClassloader(NavajoClassSupplier classloader) {
		myClassloader = classloader;
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
	public boolean isAsyncEnabled() {
		return false;
	}

	@Override
	public boolean isEnableStatisticsRunner() {
		return false;
	}

	@Override
	public boolean isIntegrityWorkerEnabled() {
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
	public File getJarFolder() {
		return null;
	}

	@Override
	public String getClassPath() {
		return null;
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
	public void startStatisticsRunner() {
		
		
	}

	@Override
	public void startTaskRunner() {
			
	}
	
	public static void main(String [] args) throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "Input");
		doc.addMessage(m);
		doc.write(System.err);
		TestNavajoConfig tnc = new TestNavajoConfig();
		tnc.writeConfig("aap.xml", doc);
		Navajo read = NavajoFactory.getInstance().createNavajo(tnc.getConfig("aap.xml"));
		read.write(System.err);
		
		
	}

	@Override
	public String getCompilationLanguage() {
		
		return "javascript";
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



}
