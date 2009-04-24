package com.dexels.navajo.tipi.projectbuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dexels.navajo.tipi.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class LocalJnlpBuilder extends BaseJnlpBuilder {



	@Override
	public String getJnlpName() {
		return "LocalJnlp.jnlp";
	}



	@Override
	protected void appendProxyResource(XMLElement resources, String repository,
			String mainExtension) {
		// do nothing
		
	}

	@Override
	protected boolean appendResourceForExtension(XMLElement resources,
			String repository, String ext,String version) {
		Map<String,String> mainJar = new HashMap<String,String>();
		Map<String,String> remoteJar = new HashMap<String,String>();
		List<String> jars = ExtensionManager.getJars(repository, ext,version,mainJar,remoteJar);
		System.err.println("Local jars:"+ jars);
		for (String path : jars) {
			XMLElement x = new CaseSensitiveXMLElement("jar");
			resources.addChild(x);
			x.setAttribute("href", "lib/"+path);
			String main = mainJar.get(path);
			x.setAttribute("main", main==null?"false":"true");
		}
		for (Entry<String,String> e : remoteJar.entrySet()) {
			XMLElement x = new CaseSensitiveXMLElement("extension");
			resources.addChild(x);
			x.setAttribute("href", e.getValue());
			x.setAttribute("name", e.getKey());
			
		}
		return false;
	}

}
