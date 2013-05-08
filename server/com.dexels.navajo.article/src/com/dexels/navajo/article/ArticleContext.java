package com.dexels.navajo.article;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.article.command.ArticleCommand;

public interface ArticleContext {

	public ArticleCommand getCommand(String name);

	public void interpretArticle(File article, ArticleRuntime ac) throws IOException,
			ArticleException;

	public File resolveArticle(String pathInfo);
	
	public List<String> listArticles();
	
	public void writeArticleMeta(String name,ObjectNode rootNode, ObjectMapper mapper) throws ArticleException, IOException;
}
