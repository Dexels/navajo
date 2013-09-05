package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;

public interface ApplicationStatus {

	public File getAppFolder();

	public List<String> getProfiles();

	public void setManager(ApplicationManager manager);

	public String getApplicationName();

	public String getSettingString(String key);

	public ResourceBundle getSettingsBundle();

}