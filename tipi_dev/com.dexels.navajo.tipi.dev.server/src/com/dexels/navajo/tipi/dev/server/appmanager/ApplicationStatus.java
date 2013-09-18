package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;

public interface ApplicationStatus {

	public File getAppFolder();

	public List<String> getProfiles();

	public String getApplicationName();

	public String getSettingString(String key);

	public ResourceBundle getSettingsBundle();

	public List<Dependency> getDependencies();

	public void load() throws IOException;

}