/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article.command.impl;

import java.util.Map;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	
	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws APIException {
		String value = parameters.get("value");
		if(value.startsWith("@")) {
			return runtime.getObjectMapper().getNodeFactory().textNode(runtime.resolveArgument(value));
		}
		if(value.startsWith("$")) {
			return runtime.getObjectMapper().getNodeFactory().textNode(""+runtime.resolveScope(value));
		}
		throw new APIException("Weird value in valuecommand: " + value, null, APIErrorCode.InternalError);
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}
}
