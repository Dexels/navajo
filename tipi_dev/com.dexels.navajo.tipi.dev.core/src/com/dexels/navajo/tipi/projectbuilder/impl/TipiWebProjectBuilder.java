package com.dexels.navajo.tipi.projectbuilder.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.projectbuilder.TipiProjectBuilder;
import com.dexels.navajo.tipi.util.XMLElement;

public class TipiWebProjectBuilder extends TipiProjectBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiWebProjectBuilder.class);
	
	@Override
	public void downloadExtensionJars(String projectName, String version, URL remoteExtensionUrl, XMLElement extensionElement,
			File baseDir, boolean clean, boolean localSign) throws MalformedURLException, IOException {
		URL unsigned = new URL(remoteExtensionUrl, "lib/");
		File webInf = new File(baseDir,"WEB-INF");
		if(!webInf.exists()) {
			webInf.mkdirs();
		}
		File webInfLib = new File(webInf,"lib");
		if(webInfLib.exists()) {
			webInfLib.delete();
		}
		webInfLib.mkdirs();
		List<XMLElement> jars = extensionElement.getAllElementsByTagName("jar");
		for (XMLElement element : jars) {
			String path = element.getStringAttribute("path");
			URL jar = new URL(unsigned, path);
			try {
				ClientActions.downloadFile(jar, path, webInfLib, clean, false);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		downloadProjectInclude(remoteExtensionUrl, baseDir, clean);

	}



}
