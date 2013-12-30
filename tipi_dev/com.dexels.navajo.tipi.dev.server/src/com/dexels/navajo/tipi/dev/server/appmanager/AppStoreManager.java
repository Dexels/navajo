package com.dexels.navajo.tipi.dev.server.appmanager;

import java.util.List;
import java.util.Set;

import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public interface AppStoreManager {
	public String getCodeBase();

	public List<TipiCallbackSession> getSessionsForApplication(String application);

	public String getClientId();
	
	public String getClientSecret();
	
	// Members of this GitHub organization will be granted access
	public String getOrganization();

	public String getApplicationName();

	public String getManifestCodebase();

	public Set<String> listApplications();

	public int getSessionCount();

}
