package com.dexels.navajo.article.command.impl;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.dexels.navajo.article.ArticleClientException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ValueCommand implements ArticleCommand {

	private String name;
	
	
	public ValueCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public ValueCommand(String name) {
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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException, DirectOutputThrowable, ArticleClientException {
		String value = parameters.get("value");
		if(value.startsWith("@")) {
			return runtime.getObjectMapper().getNodeFactory().textNode(runtime.resolveArgument(value));
		}
		if(value.startsWith("$")) {
			return runtime.getObjectMapper().getNodeFactory().textNode(""+runtime.resolveScope(value));
		}
		throw new ArticleException("Weird value in valuecommand: "+value);
	}
	


	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}

}
