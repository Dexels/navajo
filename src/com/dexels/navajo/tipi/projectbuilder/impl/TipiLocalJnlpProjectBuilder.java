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

	public void downloadExtensionJars(String extensionName, String version, URL remoteExtensionUrl, XMLElement extensionElement,
			File baseDir, boolean clean, boolean localSign) throws MalformedURLException, IOException {
		
		String oldVersion = getCurrentExtensionVersion(baseDir,extensionName);
		System.err.println(">>>>>>\n>>>>>>>>\nVersion detected "+oldVersion+"\n>>>>>>>>>>\n>>>>>>>>>");
		if(version.equals(oldVersion)) {
			System.err.println("Equeal version of extension: "+extensionName+" so should skip");
			return;
		}
		URL jarFolder = null;
		if (localSign) {
			System.err.println("Local signing, so downloading unsigned binaries");
			jarFolder = new URL(remoteExtensionUrl, "unsigned/");
		} else {
			jarFolder = new URL(remoteExtensionUrl, "lib/");
		}

		List<XMLElement> jars = extensionElement.getAllElementsByTagName("jar");
		File f = null;
		if(localSign) {
			f = new File(baseDir, "unsigned");
		} else {
			f = new File(baseDir, "lib");
		}
		
		if (f.exists()) {
			f.delete();
		} 
		File libDir = new File(baseDir, "lib");
		if(!libDir.exists()) {
			libDir.mkdir();
		}
		f.mkdirs();
		for (XMLElement element : jars) {
			String path = element.getStringAttribute("path");
			URL jar = new URL(jarFolder, path);
			if (useJnlpVersioning()) {
				String packPath = path.substring(0, path.length() - 4) + "__V" + version + ".jar.pack.gz";
				URL packedJar = new URL(jarFolder, path + ".pack.gz");
				path = path.substring(0, path.length() - 4) + "__V" + version + ".jar";
				ClientActions.downloadFile(jar, path, f, clean,true);
				if(!localSign) {
					ClientActions.downloadFile(packedJar, packPath, f, clean, useJnlpVersioning());
				}
			} else {
				ClientActions.downloadFile(jar, path, f, clean, useJnlpVersioning());
			}
		}

		XMLElement main = extensionElement.getElementByTagName("main");
		if (main != null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(jarFolder, path);
			if(useJnlpVersioning()) {
				path = path.substring(0,path.length()-4)+"__V"+version+".jar";
			}
			try {
				ClientActions.downloadFile(jar, path, f,clean,useJnlpVersioning());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
//		downloadProjectInclude(extensionName, remoteExtensionUrl, extensionElement, baseDir, clean);
	}

}
