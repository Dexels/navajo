package com.dexels.navajo.article.command;

import java.util.Map;

import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface ArticleCommand {
    public String getName();

    public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String, String> parameters, XMLElement element)
            throws APIException, NoJSONOutputException;

    public boolean writeMetadata(XMLElement e, ArrayNode outputArgs, ObjectMapper mapper);
}
