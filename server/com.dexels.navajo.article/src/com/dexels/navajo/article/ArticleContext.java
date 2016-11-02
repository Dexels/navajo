package com.dexels.navajo.article;

import java.io.File;
import java.util.List;
import java.util.Map;


import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.oauth.api.exception.ScopeStoreException;
import com.dexels.oauth.api.exception.TokenStoreException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ArticleContext {

	public ArticleCommand getCommand(String name);

	public void interpretArticle(File article, ArticleRuntime ac) throws APIException, NoJSONOutputException;

	public File resolveArticle(String pathInfo);
	
	public List<String> listArticles();
	
	public void writeArticleMeta(String name,ObjectNode rootNode, ObjectMapper mapper, boolean extended) throws APIException;

	public Map<String, Object> getScopes(String token) throws ScopeStoreException, TokenStoreException;
}
