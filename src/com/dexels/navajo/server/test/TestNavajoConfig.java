package com.dexels.navajo.server.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.SimpleRepository;
import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;

public class TestNavajoConfig implements NavajoConfigInterface {

	String name = "testinstance";
	String group = "testgroup";
	
	private PersistenceManager myPersistenceManager;
//	private Repository myRepository;
	private NavajoClassSupplier myClassloader;
	private StatisticsRunnerInterface myStatisticsRunner;
	
	public TestNavajoConfig() {
		setClassloader( new NavajoClassLoader(this.getClass().getClassLoader()));
	}
	
	private File getTempFile(String name) {
		File tempFile = new File(System.getProperty("java.io.tmpdir"), name);
		return tempFile;
	}
	
	public TestNavajoConfig(String name, String group) {
		this.name = name;
		this.group = group;
	}
	
	public String getInstanceName() {
		return name;
	}

	public PersistenceManager getPersistenceManager() {
		if ( myPersistenceManager == null ) {
			myPersistenceManager = PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
		}
		return myPersistenceManager;
	}

	public Navajo readConfig(String s) throws IOException {
		Navajo config = NavajoFactory.getInstance().createNavajo(getConfig(s));
		return config;
	}

	public String getConfigPath() {
		return new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
	}

	public String getInstanceGroup() {
		return group;
	}

	public double getCurrentCPUload() {
		
		return 0;
	}

	public Repository getRepository() {
		return new SimpleRepository();
	}

	public void setMyPersistenceManager(PersistenceManager myPersistenceManager) {
		this.myPersistenceManager = myPersistenceManager;
	}

//	public void setMyRepository(Repository myRepository) {
//		this.myRepository = myRepository;
//	}

	public void writeConfig(String name, Navajo conf) throws IOException {
		File f = getTempFile(name);
		System.err.println("IN WRITECONFIG(" + name + "): " + f.getAbsolutePath());
		FileWriter fw = new FileWriter(f);
		try {
			conf.write(fw);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		fw.close();
	}

	public NavajoClassSupplier getClassloader() {
		return myClassloader;
	}

	public void setClassloader(NavajoClassSupplier classloader) {
		myClassloader = classloader;
	}

	public StatisticsRunnerInterface getStatisticsRunner() {
		return myStatisticsRunner;
	}

	public String getRootPath() {
		return getConfigPath();
	}

	public String getAdapterPath() {
		
		return null;
	}

	public AsyncStore getAsyncStore() {
		
		return null;
	}

	public float getAsyncTimeout() {
		
		return 0;
	}

	public NavajoClassLoader getBetaClassLoader() {
		return (NavajoClassLoader) getClassloader();
	}

	public String getBetaUser() {
		
		return "beta";
	}

	public String getCompiledScriptPath() {
		
		return null;
	}

	public String getDbPath() {
		
		return null;
	}

	public DescriptionProviderInterface getDescriptionProvider() {
		
		return null;
	}

	public int getMonitorExceedTotaltime() {
		
		return 0;
	}

	public String getMonitorUsers() {
		
		return null;
	}

	public String getMonitorWebservices() {
		
		return null;
	}

	public HashMap<String, String> getProperties() {
		
		return null;
	}

	public String getResourcePath() {
		
		return null;
	}

	public String getScriptPath() {
		
		return null;
	}

	public boolean isAsyncEnabled() {
		
		return false;
	}

	public boolean isEnableStatisticsRunner() {
		
		return false;
	}

	public boolean isIntegrityWorkerEnabled() {
		
		return false;
	}

	public boolean isLockManagerEnabled() {
		
		return false;
	}

	public boolean isMonitorOn() {
		
		return false;
	}

	public boolean isStatisticsRunnerEnabled() {
		
		return false;
	}

	public void setMonitorExceedTotaltime(int monitorExceedTotaltime) {
		
		
	}

	public void setMonitorOn(boolean b) {
		
		
	}

	public void setMonitorUsers(String monitorUsers) {
		
		
	}

	public void setMonitorWebservices(String monitorWebservices) {
		
		
	}

	public void setStatisticsRunnerEnabled(boolean b) {
		
		
	}

	public boolean needsFullAccessLog(Access a) {
		
		return false;
	}

	public InputStream getScript(String name) throws IOException {
		
		return null;
	}

	public void doClearCache() {
		
		
	}

	public InputStream getConfig(String name) throws IOException {
		File f = getTempFile(name);
		return new FileInputStream(f);
	}

	public boolean isCompileScripts() {
		
		return false;
	}

	public File getJarFolder() {
		
		return null;
	}

	public String getClassPath() {
		
		return null;
	}

	public void doClearScriptCache() {
		
		
	}

	public WorkerInterface getIntegrityWorker() {
		
		return null;
	}

	public LockManager getLockManager() {
		
		return null;
	}

	public int getMaxAccessSetSize() {
		
		return 0;
	}

	public void setRepository(Repository newRepository) {
		
		
	}

	public void startJabber() {
		
		
	}

	public void startStatisticsRunner() {
		
		
	}

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

	public Message getMessage(String msg) {
		
		return null;
	}

	public InputStream getResourceBundle(String name) throws IOException {
		
		return null;
	}

	public String getCompilationLanguage() {
		
		return "javascript";
	}

	public File getContextRoot() {
		
		return null;
	}

}
