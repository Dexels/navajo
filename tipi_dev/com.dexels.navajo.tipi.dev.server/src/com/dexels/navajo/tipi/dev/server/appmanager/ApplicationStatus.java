package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;

@JsonIgnoreProperties({"settingsBundle","dependencies"})
public interface ApplicationStatus {
	
	public final static String STATUS_MISSING = "MISSING";
	public final static String STATUS_OK = "OK";
	public final static String STATUS_OUTDATED = "OUTDATED";

	public File getAppFolder();

	public List<String> getProfiles();

	public String getApplicationName();

	public String getSettingString(String key);

	public ResourceBundle getSettingsBundle();

	public List<Dependency> getDependencies();

	public void load() throws IOException;

}