package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public interface ApplicationStatus {

	public File getAppFolder();

	public List<String> getProfiles();

	public String getApplicationName();

	public String getSettingString(String key);

	public ResourceBundle getSettingsBundle();

}