package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.File;

public interface ApplicationManager {

	public static final String TIPI_STORE_APPLICATION = "tipi.store.application";
	public static final int SLEEP_TIME = 3000;

	public File getAppsFolder();

}