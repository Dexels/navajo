package com.dexels.navajo.article.command.impl;

import java.util.Map;

import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LiteralCommand implements ArticleCommand {

	private String name;
	
	
	public LiteralCommand() {
		// default con    structor
	}
	
	// for testing, no need to call activate this way
	public LiteralCommand(String name) {
		this.name = name;
	}
	
	public void activate(Map<String,String> settings) {
		this.name = settings.get("command.name");
	}
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws APIException, NoJSONOutputException {
		ObjectNode root = runtime.getRootNode();
		String name = element.getAttribute("name", "name").toString();
		String value = element.getAttribute("target", "").toString();
		
		root.put(name, value);
		return root;
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs, ObjectMapper mapper) {
		ObjectNode root = mapper.createObjectNode();
				
		String name = e.getAttribute("name", "name").toString();
		String value = e.getAttribute("target", "").toString();
		
		root.put(name, value);
		
		outputArgs.add(root);
		return true;
	}
}
