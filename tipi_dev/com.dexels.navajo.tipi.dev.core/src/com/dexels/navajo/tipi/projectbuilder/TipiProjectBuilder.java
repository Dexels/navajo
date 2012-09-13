package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

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
	
	public void downloadProjectInclude(URL remoteExtensionUrl,  File baseDir, boolean clean) throws MalformedURLException, IOException {
		URL projectInclude = new URL(remoteExtensionUrl,"projectinclude.zip");
	   try {
			ClientActions.downloadFile(projectInclude, "projectinclude.zip", baseDir, clean, false);
			File projectZip = new File(baseDir,"projectinclude.zip");
			ClientActions.unzip(projectZip, baseDir);
	   } catch (Exception e) {
			System.err.println("No project include found!");
		}

	}

	// Will return a version label for a certain extension, based on the previous build, or null when things are amiss.
	protected String getCurrentExtensionVersion(File baseDir, String extensionName) throws IOException {
		File previousBuild = new File(baseDir,"settings/buildresults.properties");
		if(!previousBuild.exists()) {
			return null;
		}
		FileInputStream is = new FileInputStream(previousBuild);
		PropertyResourceBundle pe = new PropertyResourceBundle(is);
		String extensions = pe.getString("extensions");
		StringTokenizer st = new StringTokenizer(extensions,",");
		while(st.hasMoreTokens()) {
			String current = st.nextToken();
			if(current.indexOf("/")==-1 ) {
				System.err.println("No version in version result: "+extensions+" currently checking: "+current);
				return null;
			}
			StringTokenizer st2 = new StringTokenizer(current,"/");
			String name = st2.nextToken();
			if(!name.equals(extensionName)) {
				continue;
			}
			String version = st2.nextToken();
			return version;
		}
		return null;
	}

}
