package com.dexels.navajo.tipi.projectbuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class RemoteJnlpBuilder extends BaseJnlpBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(RemoteJnlpBuilder.class);
	@Override
	public String getJnlpName() {
		return "RemoteJnlp.jnlp";
	}

	@Override
	protected boolean appendResourceForExtension(XMLElement resources,
			String repository, String ext,String version,String resourceBase, boolean useVersioning) throws IOException {
		try {
			VersionResolver vr = new VersionResolver(repository);
			
			URL rep = new URL(repository + vr.resultVersionPath(ext) + "/" + ext + ".jnlp");
			// logger.info("Repos: "+rep);
			// resources.addTagKeyValue("extension","").setAttribute("href",
			// rep);
			XMLElement xe = new CaseSensitiveXMLElement("extension");
			resources.addChild(xe);
			xe.setAttribute("href", rep.toString());
			return ExtensionManager.isMainExtension(repository, ext,version);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("Error: ",e);
		}
		return false;
	}

	protected void appendProxyResource(XMLElement resources, String repository,
			String mainExtension, boolean useVersioning) throws IOException {
		Map<String,String> versionMap = myVersionResolver.resolveVersion(mainExtension);
		
		Map<String, String> mm = ExtensionManager.getMain(repository,
				versionMap.get("extension"),versionMap.get("version"));
		XMLElement res = resources.addTagKeyValue("jar", "");
		res.setAttribute("href", "lib/" + mm.get("href"));
		res.setAttribute("main", "true");
	}
//
//	public static void downloadProxy(String project, URL projectURL,
//			XMLElement result, File baseDir) throws MalformedURLException {
//		logger.info("PRoject dir: " + projectURL);
//		URL unsigned = new URL(projectURL, "lib/");
//
//		List<XMLElement> jars = result.getElementsByTagName("jar");
//		File f = new File(baseDir, "lib");
//		if (f.exists()) {
//			f.delete();
//		}
//		f.mkdirs();
//		for (XMLElement element : jars) {
//			String path = element.getStringAttribute("path");
//			URL jar = new URL(unsigned, path);
//			logger.info("URL: " + jar);
//			try {
//				downloadJar(jar, path, f);
//			} catch (IOException e) {
//				logger.error("Error: ",e);
//			}
//		}
//		XMLElement main = result.getElementByTagName("main");
//		if (main != null) {
//			String path = main.getStringAttribute("proxyjar");
//			URL jar = new URL(unsigned, path);
//			try {
//				downloadJar(jar, path, f);
//			} catch (IOException e) {
//				logger.error("Error: ",e);
//			}
//		}
//	}

}
