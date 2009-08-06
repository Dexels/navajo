package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.tipi.util.XMLElement;

public abstract class TipiProjectBuilder {
	private boolean useVersioning;
	public abstract void downloadExtensionJars(String extensionName, String version, URL remoteExtensionUrl, XMLElement extensionElement, File baseDir, boolean clean, boolean localSign) throws MalformedURLException, IOException;

	
	public void setUseVersioning(boolean b) {
		useVersioning = b;
	}
	
	protected boolean useJnlpVersioning() {
		return useVersioning;
	}
	
	public void downloadProjectInclude(String extensionName, URL remoteExtensionUrl, XMLElement extensionElement, File baseDir, boolean clean) throws MalformedURLException, IOException {
		URL projectInclude = new URL(remoteExtensionUrl,"projectinclude.zip");
	   try {
			ClientActions.downloadFile(projectInclude, "projectinclude.zip", baseDir, clean, false);
			File projectZip = new File(baseDir,"projectinclude.zip");
			ClientActions.unzip(projectZip, baseDir);
	   } catch (Exception e) {
			System.err.println("No project include found!");
		}

	}

}
