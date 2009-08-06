package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class WebDescriptorBuilder extends BaseDeploymentBuilder {

	@Override
	public void build(String repository, String developmentRepository, String extensions, File baseDir, String codebase, String fileName, String profile, boolean useVersioning)
			throws IOException {
		
//		ClientActions.downloadFile(new URL(developmentRepository+"TemplateEchoProject/web.xml"), "WEB-INF/web.xml", baseDir, false, true);
		File webInf = new File(baseDir,"WEB-INF");
		if(!webInf.exists()) {
			webInf.mkdirs();
		}
		File webXml = new File(webInf,"web.xml");
		if(!webXml.exists()) {
			createBlankWebXml(developmentRepository,webInf);
		}
		
		XMLElement web = new CaseSensitiveXMLElement();
		FileReader reader = new FileReader(webXml);
		web.parseFromReader(reader);
		reader.close();
		
		XMLElement servlet =  web.getElementByTagName("servlet");
		Map<String,String> params = parseParams(baseDir);
		Map<String,String> arguments = parseArguments(baseDir, profile);
		Vector<XMLElement> existing = servlet.getAllElementsByTagName("init-param");
		
		for (Entry<String, String> e : arguments.entrySet()) {
			for (XMLElement element : existing) {
			 	String currentParamName = element.getElementByTagName("param-name").getContent();
			 	if(e.getKey().equals(currentParamName)) {
			 		servlet.removeChild(element);
			 	}
			}

			XMLElement init = new CaseSensitiveXMLElement("init-param");
			init.addTagKeyValue("param-name", e.getKey());
			init.addTagKeyValue("param-value", e.getValue());
			servlet.addChild(init);
		}

		XMLElement session = web.getElementByTagName("session-timeout");
		session.setContent(arguments.get("sessionTimeout"));
		XMLElement description = web.getElementByTagName("description");
		description.setContent(params.get("title"));
		System.err.println("Web xml: "+web);
		FileWriter writer = new FileWriter(new File(baseDir,"WEB-INF/web.xml"));
		web.write(writer);
		writer.flush();
		writer.close();
	}

	private void createBlankWebXml(String developmentRepository, File webDir) throws WriteAbortedException, MalformedURLException, IOException {
		ClientActions.downloadFile(new URL(developmentRepository+"web.xml"), "web.xml", webDir, false, false);
		
	}

}
