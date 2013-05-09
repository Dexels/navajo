package com.dexels.navajo.article.command.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ElementCommand implements ArticleCommand {

	private String name;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ElementCommand.class);
	
	public ElementCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public ElementCommand(String name) {
		this.name = name;
	}
	
	public void activate(Map<String,String> settings) {
		this.name = settings.get("command.name");
	}
	@Override
	public String getName() {
		return name;
	}

//    <element service="clubsites/nl/adresboek" name="parameters/achternaam" showlabel="true"/>

	
	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException {
		String service = parameters.get("service");
		Navajo current = null;
		if(service==null) {
			current = runtime.getNavajo();
		} else {
			current = runtime.getNavajo(service);
		}
		if(current==null) {
			throw new ArticleException("No current navajo found.");
		}
		String name = parameters.get("name");
		if(name==null) {
			throw new ArticleException("No 'name' parameter found in element. This is required.");
		}
		Property p = current.getProperty(name);
		if(p==null) {
			current.write(System.err);
			throw new ArticleException("No property: "+name+" found in current navajo.");
		}
//		boolean writeLabel = "true".equals(parameters.get("showlabel"));
//		if(writeLabel) {
//			
//		}
		ObjectNode on = runtime.getObjectMapper().createObjectNode();
		on.put(name, p.getValue());
//		try {
//			printElementJSONTypeless(p, runtime.getOutputWriter());
//		} catch (IOException e) {
//			logger.error("Error: ", e);
//		}
		return on;
	}
	


	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}

}
