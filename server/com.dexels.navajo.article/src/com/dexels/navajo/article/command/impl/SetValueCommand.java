package com.dexels.navajo.article.command.impl;

import java.util.Map;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;

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
	public void execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters) throws ArticleException {
		
	}

}
