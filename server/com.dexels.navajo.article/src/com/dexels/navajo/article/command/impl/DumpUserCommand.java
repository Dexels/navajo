package com.dexels.navajo.article.command.impl;

import java.util.Map;
import java.util.Map.Entry;

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

public class DumpUserCommand implements ArticleCommand {

	private String name;
	
	
	public DumpUserCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public DumpUserCommand(String name) {
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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException, DirectOutputThrowable {
		String token = runtime.getPassword();
		System.err.println("Token: "+token);
//		String value = parameters.get("value");
		Map<String,Object> user = runtime.getSuppliedScopes();
		ObjectNode on = runtime.getRootNode();
		for (Entry<String,Object> e : user.entrySet()) {
			on.put(e.getKey(), ""+e.getValue());
		}
		return on;
	}
	


	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}

}
