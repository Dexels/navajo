package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreManager;
import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public class AppStoreManagerImpl implements AppStoreManager {

	private String codebase;
	private final Set<TipiCallbackSession> sessionSet = new HashSet<TipiCallbackSession>();
	protected String clientid;
	protected String clientsecret;
	protected String organization;
	private String applicationName;
	private String manifestCodebase;
	private boolean authorize=true;
	
	private final Map<String, RepositoryInstance> repositories = new HashMap<String, RepositoryInstance>();
	private final Map<RepositoryInstance, Map<String, Object>> repositorySettings = new HashMap<RepositoryInstance, Map<String, Object>>();

	
	public void addTipiSession(TipiCallbackSession tcs) {
		sessionSet.add(tcs);
	}

	public void removeTipiSession(TipiCallbackSession tcs) {
		sessionSet.remove(tcs);
	}
	

	public void addRepositoryInstance(RepositoryInstance a,
			Map<String, Object> settings) {
		repositories.put(a.getRepositoryName(), a);
		repositorySettings.put(a, settings);
	}

	public void removeRepositoryInstance(RepositoryInstance a) {
		repositories.remove(a.getRepositoryName());
		repositorySettings.remove(a);
	}
	
	@Override
	public List<TipiCallbackSession> getSessionsForApplication(String application) {
		List<TipiCallbackSession> result = new ArrayList<TipiCallbackSession>();
		for (TipiCallbackSession tipiCallbackSession : sessionSet) {
			if(application.equals(tipiCallbackSession.getApplication())) {
				result.add(tipiCallbackSession);
			}
		}
		return result;
	}
	
	public void activate(Map<String,Object> configuration) throws IOException {
		clientid = (String) configuration.get("tipi.store.clientid");
		clientsecret = (String) configuration.get("tipi.store.clientsecret");
		organization = (String) configuration.get("tipi.store.organization");
		applicationName = (String) configuration.get("tipi.store.applicationname");
		manifestCodebase = (String) configuration.get("tipi.store.manifestcodebase");
		codebase = (String)configuration.get("tipi.store.codebase");
		Boolean auth = (Boolean)configuration.get("authorize");
		if(auth!=null) {
			authorize = auth;
		}
	}
	
	public void deactivate() {
	}

	@Override
	public String getCodeBase() {
		return codebase;
	}

	@Override
	public String getClientId() {
		return this.clientid;
	}


	@Override
	public String getClientSecret() {
		return this.clientsecret;
	}


	@Override
	public String getOrganization() {
		return this.organization;
	}
	
	@Override
	public String getApplicationName() {
		return this.applicationName;
	}
	
	@Override
	public String getManifestCodebase() {
		return this.manifestCodebase;
	}

	@Override
	public Set<String> listApplications() {
		
		return repositories.keySet();
	}

	@Override
	public int getSessionCount() {
		return sessionSet.size();
	}

	@Override
	public boolean useAuthorization() {
		return authorize;
	}

}
