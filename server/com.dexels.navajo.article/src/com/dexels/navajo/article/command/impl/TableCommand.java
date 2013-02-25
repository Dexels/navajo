package com.dexels.navajo.article.command.impl;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

public class TableCommand implements ArticleCommand {

	private String name;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TableCommand.class);
	
	public TableCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public TableCommand(String name) {
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
	public boolean execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters) throws ArticleException {
//		runtime.setMimeType("text/plain");
		String service = parameters.get("service");
		if(service==null) {
			throw new ArticleException("No service parameter supplied for table.");
		}
		String path = parameters.get("path");
		if(path==null) {
			throw new ArticleException("No path parameter supplied for table.");
		}
		Navajo n = runtime.getNavajo(service);
		if(n==null) {
			throw new ArticleException("Navajo: "+service+" was not found in table command");
		}
		Message m = n.getMessage(path);
//		if(m==null) {
//			n.write(System.err);
//			throw new ArticleException("Path: "+path+" was not found in navajo : "+service);
//		}
		try {
			runtime.setMimeType("text/json");
			runtime.getOutputWriter().write("\""+parameters.get("name")+"\" : ");
			if (m==null) {
				logger.warn("Ignoring table command. Message: {} not found. Dumping all.",path);
				n.writeJSONTypeless(runtime.getOutputWriter());

			} else {
				m.writeSimpleJSON(runtime.getOutputWriter());
			}
		} catch (IOException e) {
			throw new ArticleException("Error writing result", e);
		}
		return true;
//		m.write(System.err);
	}

}
