package com.dexels.navajo.tipi.projectbuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.tipi.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class RemoteJnlpBuilder extends BaseJnlpBuilder {



	@Override
	public String getJnlpName() {
		return "RemoteJnlp.jnlp";
	}

	@Override
	protected boolean appendResourceForExtension(XMLElement resources, String repository, String ext) {
		try {
			URL rep = new URL(repository+ext+"/"+ext+".jnlp");
//			System.err.println("Repos: "+rep);
			//resources.addTagKeyValue("extension","").setAttribute("href", rep);
			XMLElement xe = new CaseSensitiveXMLElement("extension");
			resources.addChild(xe);
			xe.setAttribute("href", rep.toString());
			return ExtensionManager.isMainExtension(repository, ext);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
	
	protected void appendProxyResource(XMLElement resources, String repository, String mainExtension) {
		Map<String,String> mm = ExtensionManager.getMain(repository, mainExtension);
		XMLElement res = resources.addTagKeyValue("jar","");
		res.setAttribute("href", "lib/"+mm.get("href"));
	}


}
