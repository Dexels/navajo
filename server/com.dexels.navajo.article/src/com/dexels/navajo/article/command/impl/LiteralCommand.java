package com.dexels.navajo.article.command.impl;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class LiteralCommand implements ArticleCommand {

	private String name;
	
	
	public LiteralCommand() {
		// default constructor
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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException, DirectOutputThrowable {
		ObjectNode root = runtime.getRootNode();
		String name = element.getAttribute("name", "name").toString();
		String value = element.getAttribute("value", "").toString();
		
		root.put(name, value);
		return root;
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs, ObjectMapper mapper) {
		ObjectNode root = mapper.createObjectNode();
				
		String name = e.getAttribute("name", "name").toString();
		String value = e.getAttribute("value", "").toString();
		
		root.put(name, value);
		
		outputArgs.add(root);
		return true;
	}
}
