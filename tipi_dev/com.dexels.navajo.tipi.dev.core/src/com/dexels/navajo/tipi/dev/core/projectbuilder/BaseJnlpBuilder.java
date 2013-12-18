package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;

public abstract class BaseJnlpBuilder extends BaseDeploymentBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseJnlpBuilder.class);
	

	protected boolean appendResourceForExtension(XMLElement resources,
			String name, String version, boolean main) throws IOException {
		XMLElement x = new CaseSensitiveXMLElement("jar");
		resources.addChild(x);
		if(version!=null) {
			x.setAttribute("version", version);
		}
		x.setAttribute("href", "lib/" + name);
		if(main) {
			x.setAttribute("main", "true");
		}
		return false;
	}


	protected abstract void appendProxyResource(XMLElement resources, String repository, String mainExtension, boolean useVersioning) throws IOException;
	public abstract String getJnlpName();


	public String build(String repository, String developRepository, String extensions, Map<String,String> tipiProperties, String deployment,  File baseDir, String codebase, List<String> profiles, boolean useVersioning) {
		for (String fileName : profiles) {
			File jnlpFile = new File(baseDir, fileName+".jnlp");
			logger.debug("Writing jnlp: "+jnlpFile.getAbsolutePath());
			try {
				FileWriter fw1 = new FileWriter(jnlpFile);
				fw1.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//				XMLElement output = buildElement(repository, extensions,tipiProperties, deployment,baseDir, codebase,"", fileName+".jnlp", fileName,useVersioning);
//				output.write(fw1);
				fw1.flush();
				fw1.close();
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		return null;
} 
	

	private void appendArguments(XMLElement app, XMLElement java, Map<String, String> elements) {
		for (String elt : elements.keySet()) {
			if (elt.equals("javaVm")) {
				java.setAttribute("java-vm-args", elements.get(elt));
			} else if (elt.equals("javaVersion")) {
				java.setAttribute("version", elements.get(elt));
			} else {
				XMLElement zz = new CaseSensitiveXMLElement("argument");
				zz.setContent(elt + "=" + elements.get(elt));
				app.addChild(zz);
			}
		}
	}

	private void appendSecurity(XMLElement output, String security) {

		if ("all".equals(security)) {
			XMLElement sec = output.addTagKeyValue("security", "");
			sec.addTagKeyValue("all-permissions", "");
		}
		if ("j2ee".equals(security)) {
			XMLElement sec = output.addTagKeyValue("security", "");
			sec.addTagKeyValue("j2ee-application-client-permissions", "");
		}

	}
	
	@Override
	public void buildFromMaven(ResourceBundle settings,
			List<Dependency> dependencyList, File appFolder,
			List<String> profiles, String resourceBase, String suppliedCodebase, String applicationName) {
		logger.debug("Building in folder: "+appFolder);
		if(profiles==null) {
			logger.warn("No profiles loaded, not building jnlp");
		}
		for (String fileName : profiles) {
			File jnlpFile = new File(appFolder, fileName+".jnlp");
			logger.info("Writing jnlp: "+jnlpFile.getAbsolutePath()+" with # of dependencies: "+dependencyList.size());
			try {
				FileWriter fw1 = new FileWriter(jnlpFile);
				fw1.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				XMLElement output = buildElementFromMaven(dependencyList,settings,"deployment",appFolder, resourceBase,fileName+".jnlp", fileName,suppliedCodebase,applicationName);
				output.write(fw1);
				fw1.flush();
				fw1.close();
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
	}


	private XMLElement buildElementFromMaven(List<Dependency> dependencies, ResourceBundle params,String deployment, File baseDir, String resourceBase, String fileName, String profile, String suppliedCodebase, String applicationName) throws IOException {
		XMLElement output = new CaseSensitiveXMLElement();
		output.setName("jnlp");
		output.setAttribute("version", "1");
		output.setAttribute("spec", "1.0+");
		output.setAttribute("codebase", assembleCodebase(suppliedCodebase,applicationName,"apps"));
		output.setAttribute("href", profile+".jnlp");

		XMLElement information = output.addTagKeyValue("information", "");
		information.addTagKeyValue("title", params. getString("title"));
		information.addTagKeyValue("vendor", params.getString("vendor"));
		information.addTagKeyValue("homepage", params.getString("homepage"));
		if (resourceBase!=null && !"".equals(resourceBase)) {
			information.addTagKeyValue("icon", "").setAttribute("href", resourceBase+"/"+params.getString("icon"));
		} else {
			information.addTagKeyValue("icon", "").setAttribute("href", params.getString("icon"));
		}

		logger.debug("Parsing: " + fileName);

		if (params.getString("splash") != null) {
			XMLElement splash = new CaseSensitiveXMLElement();
			splash.setName("icon");
			information.addChild(splash);
			if (resourceBase!=null && !"".equals(resourceBase)) {
				splash.setAttribute("href", resourceBase+"/" + params.getString("splash"));
			} else {
				splash.setAttribute("href",params.getString("splash"));
			}
			splash.setAttribute("kind", "splash");
		}

		XMLElement resources = output.addTagKeyValue("resources", "");
		XMLElement java = resources.addTagKeyValue("j2se", "");
		java.setAttribute("version", "1.6+");
		appendSecurity(output, params.getString("permissions"));
		for (Dependency dependency : dependencies) {
			appendResourceForExtension(resources, dependency.getFileName(),dependency.getVersion(),false);
		}
		appendResourceForExtension(resources, profile+"_jnlp.jar",null,true);
		
		XMLElement app = output.addTagKeyValue("application-desc", "");
		app.setAttribute("main-class", "tipi.MainApplication");

		Map<String, String> arguments;
		try {
			arguments = parseArguments(baseDir, profile,deployment);
		} catch (IOException e1) {
			e1.printStackTrace();
			arguments = new HashMap<String, String>();
		}

		if (resourceBase==null || "".equals(resourceBase)) {
//			arguments.put("tipiCodeBase", "tipi");
//			arguments.put("resourceCodeBase", "resource");
		} else {
			arguments.put("tipiCodeBase", resourceBase+"/tipi/");
			arguments.put("resourceCodeBase", resourceBase+"/resource/");
		}
		arguments.put("tipi.appstore.application", applicationName);
		arguments.put("tipi.appstore.websocketurl", createWebsocketUrl(suppliedCodebase, applicationName));
		arguments.put("tipi.appstore.tenant", profile);
		arguments.put("tipi.appstore.session", "notused");
		
		appendArguments(app, java, arguments);
		
//		appendArguments(app, java, params);

		File startXml = new File(baseDir,"tipi/start.xml");
		if(startXml.exists()) {
			XMLElement zz = new CaseSensitiveXMLElement("argument");
			zz.setContent("start.xml");
			app.addChild(zz);
		}
		return output;
	}

	private String createWebsocketUrl(String codebase, String applicationName) {
		String url = assembleCodebase(codebase, null,null);
		String websock = url.replaceAll("http", "ws");
		return websock+"websocket";
	}

	private String assembleCodebase(String codebase, String applicationName,String context) {
		if(codebase==null) {
			return "$$codebase";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(codebase);
		if(!codebase.endsWith("/")) {
			sb.append("/");
		}
		if(context!=null && !"".equals(context)) {
			sb.append(context);
			sb.append("/");
		}
		if(applicationName!=null && !"".equals(applicationName)) {
			sb.append(applicationName);
		}
		return sb.toString();
	}

	
}
