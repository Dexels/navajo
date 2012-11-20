package com.dexels.navajo.tipi.projectbuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class LocalJnlpBuilder extends BaseJnlpBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(LocalJnlpBuilder.class);
	

	@Override
	public String getJnlpName() {
		return "LocalJnlp.jnlp";
	}



	@Override
	protected void appendProxyResource(XMLElement resources, String repository,
			String mainExtension, boolean useVersioning) {
		// do nothing
		
	}

	@Override
	protected boolean appendResourceForExtension(XMLElement resources,
			String repository, String ext,String version,String resourceBase, boolean useVersioning) throws IOException {
		Map<String,String> mainJar = new HashMap<String,String>();
		Map<String,String> remoteJar = new HashMap<String,String>();
		List<String> jars = ExtensionManager.getJars(repository, ext,version,mainJar,remoteJar);
		logger.info("Local jars:"+ jars+" resbase: "+resourceBase);
		for (String path : jars) {
			
			XMLElement x = new CaseSensitiveXMLElement("jar");
			resources.addChild(x);
			if(useVersioning) {
				x.setAttribute("version", version);
			}
			if (useVersioning || resourceBase==null || "".equals(resourceBase)) {
				x.setAttribute("href", "lib/"+path);
			} else {
				x.setAttribute("href", resourceBase+"/lib/"+path);
			}
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
