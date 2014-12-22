package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreData;

public class AutoBuildServiceImpl implements Runnable {

	
	private final static Logger logger = LoggerFactory
			.getLogger(AutoBuildServiceImpl.class);
	
	private AppStoreData appStoreData;
	private AppStoreOperation jnlpBuild;
//	private final Queue<String> buildQueue = new LinkedBlockingQueue<String>();
	private final ExecutorService executorService = Executors.newFixedThreadPool(1);
	protected final Map<String,RepositoryInstance> applications = new HashMap<String, RepositoryInstance>();
	private boolean active = false;
	
	public void activate(Map<String,Object> settings) {
//		Map<String, Map<String, ?>> data = appStoreData.getApplicationData();
		this.active = true;
		executorService.execute(this);
	}
	
	public void deactivate() {
		this.active = false;
		executorService.shutdownNow();
	}
	
	public void setJnlpBuild(AppStoreOperation jnlpBuild) {
		this.jnlpBuild = jnlpBuild;
	}

	public void clearJnlpBuild(AppStoreOperation jnlpBuild) {
		this.jnlpBuild = null;
	}
	
	public void setAppStoreData(AppStoreData appStoreData) {
		this.appStoreData = appStoreData;
	}

	public void clearAppStoreData(AppStoreData appStoreData) {
		this.appStoreData = null;
	}


	public void addRepositoryInstance(RepositoryInstance a) {
		applications.put(a.getRepositoryName(), a);
	}
	
	public void removeRepositoryInstance(RepositoryInstance a) {
		applications.remove(a.getRepositoryName());
	}

	
	@Override
	public void run() {
		while(active) {
			try {
				Map<String, Map<String, ?>> data = appStoreData.getApplicationData();
				 Map<String, ?> d = data.get("applications");
				
				for (Entry<String, ?> e : d.entrySet()) {
					RepositoryInstanceWrapper ri = (RepositoryInstanceWrapper) e.getValue();
					System.err.println("VAL: "+ri);
					System.err.println("Built? "+ri.isBuilt());
					
//					boolean built = (Boolean) e.getValue().get("built");
//					logger.info("Application {} built: {}",applicationId,built);
					if(!ri.isBuilt()) {
						jnlpBuild.build(ri);
					}
				}
			} catch (Throwable e) {
				logger.error("Error in autobuild. Continuing. ", e);
			} finally {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
