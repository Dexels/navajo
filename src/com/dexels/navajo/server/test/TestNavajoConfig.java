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
import com.dexels.navajo.persistence.impl.PersistenceManagerImpl;
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
	private Repository myRepository;
	private NavajoClassSupplier myClassloader;
	private StatisticsRunnerInterface myStatisticsRunner;
	
	public TestNavajoConfig() {
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
		return (PersistenceManagerImpl) PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", "");
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
		// TODO Auto-generated method stub
		return 0;
	}

	public Repository getRepository() {
		return new SimpleRepository();
	}

	public void setMyPersistenceManager(PersistenceManager myPersistenceManager) {
		this.myPersistenceManager = myPersistenceManager;
	}

	public void setMyRepository(Repository myRepository) {
		this.myRepository = myRepository;
	}

	public void writeConfig(String name, Navajo conf) throws IOException {
		File f = getTempFile(name);
		System.err.println("IN WRITECONFIG(" + name + "): " + f.getAbsolutePath());
		FileWriter fw = new FileWriter(f);
		try {
			conf.write(fw);
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fw.close();
	}

	public NavajoClassSupplier getClassloader() {
		return new NavajoClassLoader(this.getClass().getClassLoader());
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
		// TODO Auto-generated method stub
		return null;
	}

	public AsyncStore getAsyncStore() {
		// TODO Auto-generated method stub
		return null;
	}

	public float getAsyncTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	public NavajoClassLoader getBetaClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBetaUser() {
		// TODO Auto-generated method stub
		return "beta";
	}

	public String getCompiledScriptPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDbPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public DescriptionProviderInterface getDescriptionProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMonitorExceedTotaltime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMonitorUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMonitorWebservices() {
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, String> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getResourcePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getScriptPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAsyncEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnableStatisticsRunner() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isIntegrityWorkerEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLockManagerEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isMonitorOn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStatisticsRunnerEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setMonitorExceedTotaltime(int monitorExceedTotaltime) {
		// TODO Auto-generated method stub
		
	}

	public void setMonitorOn(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setMonitorUsers(String monitorUsers) {
		// TODO Auto-generated method stub
		
	}

	public void setMonitorWebservices(String monitorWebservices) {
		// TODO Auto-generated method stub
		
	}

	public void setStatisticsRunnerEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public boolean needsFullAccessLog(Access a) {
		// TODO Auto-generated method stub
		return false;
	}

	public InputStream getScript(String name) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void doClearCache() {
		// TODO Auto-generated method stub
		
	}

	public InputStream getConfig(String name) throws IOException {
		File f = getTempFile(name);
		return new FileInputStream(f);
	}

	public boolean isCompileScripts() {
		// TODO Auto-generated method stub
		return false;
	}

	public File getJarFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClassPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public void doClearScriptCache() {
		// TODO Auto-generated method stub
		
	}

	public WorkerInterface getIntegrityWorker() {
		// TODO Auto-generated method stub
		return null;
	}

	public LockManager getLockManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxAccessSetSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRepository(Repository newRepository) {
		// TODO Auto-generated method stub
		
	}

	public void startJabber() {
		// TODO Auto-generated method stub
		
	}

	public void startStatisticsRunner() {
		// TODO Auto-generated method stub
		
	}

	public void startTaskRunner() {
		// TODO Auto-generated method stub	
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

}
