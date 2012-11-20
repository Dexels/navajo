package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class WebDescriptorBuilder extends BaseDeploymentBuilder {

	
	private final static Logger logger = LoggerFactory
			.getLogger(WebDescriptorBuilder.class);
	@Override
	public String build(String repository, String developmentRepository, String extensions, Map<String,String> tipiProperties, String deployment, File baseDir, String codebase, List<String> profiles, boolean useVersioning)
			throws IOException {
		
//		ClientActions.downloadFile(new URL(developmentRepository+"TemplateEchoProject/web.xml"), "WEB-INF/web.xml", baseDir, false, true);
		File webInf = new File(baseDir,"WEB-INF");
		if(!webInf.exists()) {
			webInf.mkdirs();
		}
		File webXml = new File(webInf,"web.xml");
		webXml.delete();
//		if(!webXml.exists()) {
			createBlankWebXml(developmentRepository,webInf);
//		}
		
		XMLElement web = new CaseSensitiveXMLElement();
		FileReader reader = new FileReader(webXml);
		web.parseFromReader(reader);
		reader.close();
		

		
		for (String  profile : profiles) {
//			XMLElement servlet =  web.getElementByTagName("servlet");
			Map<String,String> arguments = parseArguments(baseDir, profile,deployment);
//			Vector<XMLElement> existing = servlet.getAllElementsByTagName("init-param");
	
			XMLElement servlet = new CaseSensitiveXMLElement("servlet");
			web.addChild(servlet);
			servlet.addTagKeyValue("servlet-name", profile);
			servlet.addTagKeyValue("servlet-class", "com.dexels.navajo.tipi.components.echoimpl.TipiServlet");
			
			for (Entry<String, String> e : arguments.entrySet()) {
//				for (XMLElement element : existing) {
//				 	String currentParamName = element.getElementByTagName("param-name").getContent();
//				 	if(e.getKey().equals(currentParamName)) {
//				 		servlet.removeChild(element);
//				 	}
//				}

				XMLElement init = new CaseSensitiveXMLElement("init-param");
				init.addTagKeyValue("param-name", e.getKey());
				init.addTagKeyValue("param-value", e.getValue());
				servlet.addChild(init);
			}
			XMLElement servletMapping = new CaseSensitiveXMLElement("servlet-mapping");
			web.addChild(servletMapping);
			servletMapping.addTagKeyValue("servlet-name", profile);
			servletMapping.addTagKeyValue("url-pattern", "/"+profile+"/*");

		}

		
		XMLElement sessionConf = web.getChildByTagName("session-config");
		XMLElement session = sessionConf.getChildByTagName("session-timeout");
		String sessionTimeout = tipiProperties.get("sessionTimeout");
		if(sessionTimeout==null) {
			sessionTimeout = "60";
		}
		if(session!=null) {
			session.setContent(sessionTimeout);
		}
		XMLElement description = web.getElementByTagName("description");
		description.setContent(tipiProperties.get("title"));
		logger.info("Web xml: "+web);
		FileWriter writer = new FileWriter(new File(baseDir,"WEB-INF/web.xml"));
		web.write(writer);
		writer.flush();
		writer.close();
		
		return "WEB-INF/ant/deployechodir.xml";
	}

	private void createBlankWebXml(String developmentRepository, File webDir) throws WriteAbortedException, MalformedURLException, IOException {
		ClientActions.downloadFile(new URL(developmentRepository+"web.xml"), "web.xml", webDir, false, false);
		
	}

}
