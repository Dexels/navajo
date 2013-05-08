package com.dexels.navajo.article.command;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public interface ArticleCommand {

	public String getName();
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException;
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper);
}
