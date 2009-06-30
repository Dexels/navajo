package com.dexels.navajo.tipi.projectbuilder.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class TipiRemoteJnlpProjectBuilder extends TipiProjectBuilder {

	public void downloadExtensionJars(String projectName, URL remoteExtensionUrl, XMLElement extensionElement, File baseDir,
			boolean clean) throws MalformedURLException, IOException {
		System.err.println("PRoject dir: " + remoteExtensionUrl);
		URL unsigned = new URL(remoteExtensionUrl, "lib/");

		File f = new File(baseDir, "lib");
		if (f.exists()) {
			f.delete();
		}
		f.mkdirs();
		XMLElement main = extensionElement.getElementByTagName("main");
		if (main != null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(unsigned, path);
			try {
				ClientActions.downloadFile(jar, path, f,clean,false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		downloadProjectInclude(projectName, remoteExtensionUrl, extensionElement, baseDir, clean);

	}

}
