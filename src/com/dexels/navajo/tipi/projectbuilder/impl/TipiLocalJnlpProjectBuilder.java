package com.dexels.navajo.tipi.projectbuilder.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class TipiLocalJnlpProjectBuilder extends TipiProjectBuilder{

	public void downloadExtensionJars(String projectName, URL remoteExtensionUrl, XMLElement extensionElement, File baseDir,
			boolean clean) throws MalformedURLException, IOException {
		URL signed = new URL(remoteExtensionUrl, "lib/");

		List<XMLElement> jars = extensionElement.getAllElementsByTagName("jar");
		File f = new File(baseDir, "lib");
		if (f.exists()) {
			f.delete();
		}
		f.mkdirs();
		for (XMLElement element : jars) {
			String path = element.getStringAttribute("path");
			URL jar = new URL(signed, path);
//			System.err.println("URL: " + jar);
			try {
				ClientActions.downloadFile(jar, path, f,clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		XMLElement main = extensionElement.getElementByTagName("main");
		if (main != null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(signed, path);
			try {
				ClientActions.downloadFile(jar, path, f,clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		downloadProjectInclude(projectName, remoteExtensionUrl, extensionElement, baseDir, clean);
	}

}
