package com.dexels.navajo.article.command.impl;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class SetValueCommand implements ArticleCommand {

	private String name;

	public SetValueCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public SetValueCommand(String name) {
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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement xmlElement) throws ArticleException {
//	    <setvalue service="clubsites/nl/init" element="parameters/poulecode" value="@poulecode"/>
		String service = parameters.get("service");
		if(service==null) {
			throw new ArticleException("Article problem in "+runtime.getArticleName()+". setvalue requires a service, which is not supplied.");
		}
		String element = parameters.get("element");
		if(element==null) {
			throw new ArticleException("Article problem in "+runtime.getArticleName()+". setvalue requires a element, which is not supplied.");
		}
		Navajo n = runtime.getNavajo(service);
		if(n==null) {
			throw new ArticleException("Article problem in "+runtime.getArticleName()+". Requested service: "+service+" is not loaded.");
		}
		Property p = n.getProperty(element);
		if(p==null) {
			throw new ArticleException("Article problem in "+runtime.getArticleName()+". Requested element: "+element+" in service "+service+" is not found.");
		}
		String value = parameters.get("value");
		if(value==null) {
			throw new ArticleException("Article problem in "+runtime.getArticleName()+". setvalue requires a value, which is not supplied.");
		}
		if(value.startsWith("@")) {
			String resolved = runtime.resolveArgument(value);
			if(resolved==null) {
				throw new ArticleException("Article problem in "+runtime.getArticleName()+". setvalue refers to argument: "+value+" which is not supplied");
			}
			p.setValue(resolved);
		} else {
			p.setValue(value);
		}
		return null;
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}

}
