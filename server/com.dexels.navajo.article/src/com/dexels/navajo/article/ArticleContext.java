package com.dexels.navajo.article;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.oauth.api.TokenException;

public interface ArticleContext {

	public ArticleCommand getCommand(String name);

	public void interpretArticle(File article, ArticleRuntime ac) throws IOException,
			ArticleException, DirectOutputThrowable, ArticleClientException;

	public File resolveArticle(String pathInfo);
	
	public List<String> listArticles();
	
	public void writeArticleMeta(String name,ObjectNode rootNode, ObjectMapper mapper, boolean extended) throws ArticleException, IOException;

	public Map<String,Object> getScopes(String token) throws TokenException;
}
