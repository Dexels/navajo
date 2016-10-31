package com.dexels.navajo.article.command.impl;

import java.util.Map;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement xmlElement) throws APIException {
		String service = parameters.get("service");
		if(service==null) {
			throw new APIException("Article problem in "+runtime.getArticleName()+". setvalue requires a service, which is not supplied.", null, APIErrorCode.InternalError);
		}
		String element = parameters.get("element");
		if(element==null) {
			throw new APIException("Article problem in "+runtime.getArticleName()+". setvalue requires a element, which is not supplied.", null, APIErrorCode.InternalError);
		}
		Navajo n = runtime.getNavajo(service);
		if(n==null) {
			throw new APIException("Article problem in "+runtime.getArticleName()+". Requested service: "+service+" is not loaded.", null, APIErrorCode.InternalError);
		}
		Property p = n.getProperty(element);
		if(p==null) {
			throw new APIException("Article problem in "+runtime.getArticleName()+". Requested element: "+element+" in service "+service+" is not found.", null, APIErrorCode.InternalError);
		}
		String value = parameters.get("value");
		if(value==null) {
			throw new APIException("Article problem in "+runtime.getArticleName()+". setvalue requires a value, which is not supplied.", null, APIErrorCode.InternalError);
		}
		if(value.startsWith("@")) {
			String resolved = runtime.resolveArgument(value);
			if(resolved!=null) {
				p.setValue(resolved);
			}
		} else if(value.startsWith("$")) {
			p.setAnyValue(runtime.resolveScope(value));
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
