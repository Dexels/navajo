package com.dexels.navajo.tipi.dev.core.projectbuilder.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.dev.core.projectbuilder.TipiProjectBuilder;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;

public class TipiRemoteJnlpProjectBuilder extends TipiProjectBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiRemoteJnlpProjectBuilder.class);
	@Override
	public void downloadExtensionJars(String extensionName, String version, URL remoteExtensionUrl, XMLElement extensionElement,
			File baseDir, boolean clean, boolean localSign) throws MalformedURLException, IOException {
		logger.info("Project dir: " + remoteExtensionUrl);
		URL signed = new URL(remoteExtensionUrl, "lib/");

		File f = new File(baseDir, "lib");
		if (f.exists()) {
			f.delete();
		}
		f.mkdirs();
		XMLElement main = extensionElement.getElementByTagName("main");
		if (main != null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(signed, path);
			if(useJnlpVersioning()) {
				path = path.substring(0,path.length()-4)+"__V"+version+".jar";
			}

//			URL jar = new URL(unsigned, path);
				ClientActions.downloadFile(jar, path, f,clean,false);
		}
//		downloadProjectInclude(extensionName, remoteExtensionUrl, extensionElement, baseDir, clean);

	}

}
