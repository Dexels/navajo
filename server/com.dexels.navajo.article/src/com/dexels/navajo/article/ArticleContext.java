package com.dexels.navajo.article;

import java.io.File;
import java.io.IOException;

import com.dexels.navajo.article.command.ArticleCommand;

public interface ArticleContext {

	public ArticleCommand getCommand(String name);

	public void interpretArticle(File article, ArticleRuntime ac) throws IOException,
			ArticleException;

	public File resolveArticle(String pathInfo);
	
}
