package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import org.omg.CORBA.VersionSpecHelper;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public abstract class BaseJnlpBuilder extends BaseDeploymentBuilder {

	protected abstract boolean appendResourceForExtension(XMLElement resources, String repository, String ext, String version)
			throws IOException;

	protected abstract void appendProxyResource(XMLElement resources, String repository, String mainExtension);


	public String appendResources(XMLElement resources, String repository, List<String> extensions) throws IOException {
		String mainExtension = null;
		for (String ext : extensions) {
			Map<String, String> versionMap = myVersionResolver.resolveVersion(ext);
			boolean isMain = appendResourceForExtension(resources, repository, versionMap.get("extension"), versionMap
					.get("version"));
			if (isMain) {
				mainExtension = ext;
			}
		}
		return mainExtension;
	}

	public abstract String getJnlpName();

	public void build(String repository, String developmentRepository, String extensions, File baseDir, String codebase, String fileName, String profile)
			throws IOException {
		myVersionResolver.load(repository);
		Map<String, String> params = parseParams(baseDir);
		File jnlpFile = new File(baseDir, fileName);
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
		information.addTagKeyValue("icon", "").setAttribute("href", params.get("icon"));

		System.err.println("Parsing: " + fileName);

		if (params.get("splash") != null) {
			XMLElement splash = new CaseSensitiveXMLElement();
			splash.setName("icon");
			information.addChild(splash);
			splash.setAttribute("href", codebase + params.get("splash"));
			splash.setAttribute("kind", "splash");
		}
		// List<String> missing =
		// ClientActions.checkExtensions(repository,extensions);
		// if(!missing.isEmpty()) {
		// throw new
		// BuildException("Requested extension(s) not available on repository: "
		// +missing);
		// }

		StringTokenizer st = new StringTokenizer(extensions, ",");
		List<String> exts = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			exts.add(st.nextToken());
		}

		XMLElement resources = output.addTagKeyValue("resources", "");
		// <j2se version="1.6+" max-heap-size="192m"/>
		XMLElement java = resources.addTagKeyValue("j2se", "");
		java.setAttribute("version", "1.6+");
		// MAX HEAP SIZE?
		// java.setAttribute("version","1.6+");
		// <j2se version="1.6+" java-vm-args="-ea -Xmx512M"/>

		appendSecurity(output, params.get("permissions"));
		String mainExtension = appendResources(resources, repository, exts);
		appendProxyResource(resources, repository, mainExtension);

		XMLElement app = output.addTagKeyValue("application-desc", "");
		app.setAttribute("main-class", "tipi.MainApplication");

		Map<String, String> arguments;
		try {
			arguments = parseArguments(baseDir, profile);
		} catch (IOException e1) {
			e1.printStackTrace();
			arguments = new HashMap<String, String>();
		}

		// new HashMap<String,String>();

		appendArguments(app, java, arguments);
		appendArguments(app, java, params);

		File startXml = new File(baseDir,"tipi/start.xml");
		if(startXml.exists()) {
			XMLElement zz = new CaseSensitiveXMLElement("argument");
			zz.setContent("start.xml");
			app.addChild(zz);
		}
		
		try {
			FileWriter fw1 = new FileWriter(jnlpFile);
			fw1.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			output.write(fw1);
			fw1.flush();
			fw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
				// app.addTagKeyValue("argument", elt +"="+elements.get(elt));
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
		// <j2ee-application-client-permissions/>

	}


}
