/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article.command.impl;

import java.util.Map;
import java.util.Map.Entry;

import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
	
	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws APIException {
//		String token = runtime.getPassword();
		ObjectNode on = runtime.getRootNode();
		ArrayNode requiredScopesObject = on.putArray("requiredScopes");
		for (String e : runtime.getRequiredScopes()) {
			requiredScopesObject.add(e);
		}
		ArrayNode scopesObject = on.putArray("suppliedScopes");
		for (String e : runtime.getSuppliedScopes()) {
			scopesObject.add(e);
		}

		on.set("scopes", scopesObject);
		ObjectNode userObject = on.putObject("user");
		for (Entry<String, Object> e : runtime.getUserAttributes().entrySet()) {
			userObject.put(e.getKey(), ""+e.getValue());
		}
		on.set("user", userObject);
		long expireTimestamp = runtime.getToken().getExpireTimestamp();
		on.put("expireTimestamp", expireTimestamp);
		return on;
	}
	
	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}
}
