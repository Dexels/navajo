package com.dexels.navajo.server.test;

import java.io.File;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.FileNavajoConfig;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.SimpleRepository;
import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.sharedstore.SharedFileStore;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class TestNavajoConfig extends FileNavajoConfig implements NavajoConfigInterface {

	String name = "testinstance";
	String group = "testgroup";
	
	private PersistenceManager myPersistenceManager;
//	private Repository myRepository;
	private NavajoClassSupplier myClassloader;
	
	public TestNavajoConfig() {
		setClassloader( new NavajoClassLoader(this.getClass().getClassLoader()));
	}
	
	public TestNavajoConfig(String name, String group) {
		this.name = name;
		this.group = group;
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
		if ( myPersistenceManager == null ) {
			myPersistenceManager = PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		}
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
		return null;
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
		return null;
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
	public boolean needsFullAccessLog(Access a) {
		return false;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}



}
