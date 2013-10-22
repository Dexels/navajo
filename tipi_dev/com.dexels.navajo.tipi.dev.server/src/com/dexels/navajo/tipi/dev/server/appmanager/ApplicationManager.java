package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public interface ApplicationManager {

	public static final String TIPI_STORE_APPLICATION = "tipi.store.application";
	public static final int SLEEP_TIME = 25000;

	public File getAppsFolder();

	public Set<String> listApplications();

	public File getStoreFolder();

	public String getCodeBase();

	public List<TipiCallbackSession> getSessionsForApplication(String application);

}