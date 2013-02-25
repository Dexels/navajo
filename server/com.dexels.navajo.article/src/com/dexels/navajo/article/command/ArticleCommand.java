package com.dexels.navajo.article.command;

import java.util.Map;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;

public interface ArticleCommand {

	public String getName();
	public boolean execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters) throws ArticleException;
}
