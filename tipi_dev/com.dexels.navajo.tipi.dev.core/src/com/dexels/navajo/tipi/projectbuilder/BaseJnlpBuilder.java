package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public abstract class BaseJnlpBuilder extends BaseDeploymentBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseJnlpBuilder.class);
	
	protected abstract boolean appendResourceForExtension(XMLElement resources, String repository, String ext, String version,String resourceBase, boolean useVersioning)
			throws IOException;

	protected abstract void appendProxyResource(XMLElement resources, String repository, String mainExtension, boolean useVersioning) throws IOException;


	public String appendResources(XMLElement resources, String repository, List<String> extensions,String resourceBase, boolean useVersioning) throws IOException {
		String mainExtension = null;
		for (String ext : extensions) {
			Map<String, String> versionMap = myVersionResolver.resolveVersion(ext);
			boolean isMain = appendResourceForExtension(resources, repository, versionMap.get("extension"), versionMap
					.get("version"),resourceBase, useVersioning);
			if (isMain) {
				mainExtension = ext;
			}
		}
		if(useVersioning) {
//			buildVersionFile(baseDir,repository, extensions);
		}
		return mainExtension;
	}

//	private void buildVersionFile(File baseDir,String repository, List<String> extensions) throws IOException {
//		XMLElement jnlpVersions = new CaseSensitiveXMLElement("jnlp-versions");
//		for (String ext : extensions) {
//			Map<String, String> versionMap = myVersionResolver.resolveVersion(ext);
//			String version = versionMap.get("version");
//			List<String> jars = ExtensionManager.getJars(repository, versionMap.get("extension"),version,new HashMap<String,String>(),new HashMap<String,String>());
//			appendExtensionToVersions(jnlpVersions, ext,version,jars);
//		}
//		logger.info("Versions: "+jnlpVersions.toString());
//		FileWriter fw = new FileWriter(new File(baseDir,"versions.xml"));
//		fw.write("<?xml version=\"1.0\"?>\n");
//		jnlpVersions.write(fw);
//		fw.flush();
//		fw.close();
//	}

//	<?xml version="1.0"?>
//	<jnlp-versions>
//	<resource>
//	<pattern>
//	<name>lib.jar</name>
//	<version-id>1.1</version-id>
//	</pattern>
//	<file>lib.jar</file>
//	</resource>
//	</jnlp-versions>
	
	
	@SuppressWarnings("unused")
	private void appendExtensionToVersions(XMLElement jnlpVersions, String ext, String version, List<String> jars) {
		for (String currentResource : jars) {
			XMLElement resource = new CaseSensitiveXMLElement("resource");
			jnlpVersions.addChild(resource);
			XMLElement pattern = new CaseSensitiveXMLElement("pattern");
			resource.addChild(pattern);
			pattern.addTagKeyValue("name", currentResource);
			pattern.addTagKeyValue("version-id", version);
//			resource.addTagKeyValue("file", currentResource.substring(0,currentResource.length()-4)+"__V"+version+".jar");
			resource.addTagKeyValue("file", "lib/"+currentResource);
		}
		
	}

	public abstract String getJnlpName();

	@Override
	public String build(String repository, String developRepository, String extensions, Map<String,String> tipiProperties, String deployment,  File baseDir, String codebase, List<String> profiles, boolean useVersioning) {
		for (String fileName : profiles) {
			File jnlpFile = new File(baseDir, fileName+".jnlp");
			logger.info("Writing jnlp: "+jnlpFile.getAbsolutePath());
			try {
				FileWriter fw1 = new FileWriter(jnlpFile);
				fw1.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				XMLElement output = buildElement(repository, extensions,tipiProperties, deployment,baseDir, codebase,"", fileName+".jnlp", fileName,useVersioning);
				output.write(fw1);
				fw1.flush();
				fw1.close();
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		return null;
} 
	public XMLElement buildElement(String repository, String extensions,Map<String, String> params,String deployment, File baseDir, String codebase, String resourceBase, String fileName, String profile, boolean useVersioning) throws IOException {
		//String repository = generalRepository+"Extensions/";
		if(!repository.endsWith("/")) {
			repository = repository+"/";
		}
		if(!repository.endsWith("Extensions/")) {
			repository = repository+ "Extensions/";
		}
		myVersionResolver.load(repository);
		
		XMLElement output = new CaseSensitiveXMLElement();
		if (!codebase.endsWith("/")) {
			codebase = codebase + "/";
		}

		output.setName("jnlp");
		output.setAttribute("version", "1");
		output.setAttribute("spec", "1.0+");
		output.setAttribute("codebase", codebase);
		output.setAttribute("href", fileName);

		XMLElement information = output.addTagKeyValue("information", "");
		information.addTagKeyValue("title", params.get("title"));
		information.addTagKeyValue("vendor", params.get("vendor"));
		information.addTagKeyValue("homepage", params.get("homepage"));
		information.addTagKeyValue("icon", "").setAttribute("href", resourceBase+"/"+params.get("icon"));

		logger.info("Parsing: " + fileName);

		if (params.get("splash") != null) {
			XMLElement splash = new CaseSensitiveXMLElement();
			splash.setName("icon");
			information.addChild(splash);
			splash.setAttribute("href", resourceBase+"/" + params.get("splash"));
			splash.setAttribute("kind", "splash");
		}

		StringTokenizer st = new StringTokenizer(extensions, ",");
		List<String> exts = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			exts.add(st.nextToken());
		}

//		XMLElement update = output.addTagKeyValue("update", "");
//		update.setAttribute("check", "always");
//		update.setAttribute("policy", "prompt-update");
		XMLElement resources = output.addTagKeyValue("resources", "");
		XMLElement java = resources.addTagKeyValue("j2se", "");
		java.setAttribute("version", "1.6+");
		appendSecurity(output, params.get("permissions"));
		String mainExtension = appendResources(resources, repository, exts,resourceBase,useVersioning);
		appendProxyResource(resources, repository, mainExtension,useVersioning);

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

		appendArguments(app, java, arguments);
		appendArguments(app, java, params);

		File startXml = new File(baseDir,"tipi/start.xml");
		if(startXml.exists()) {
			XMLElement zz = new CaseSensitiveXMLElement("argument");
			zz.setContent("start.xml");
			app.addChild(zz);
		}
		return output;
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


}
