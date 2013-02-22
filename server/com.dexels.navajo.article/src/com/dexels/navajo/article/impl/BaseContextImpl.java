package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.server.NavajoIOConfig;

public abstract class BaseContextImpl implements ArticleContext {

	private final Map<String,ArticleCommand> commands = new HashMap<String, ArticleCommand>();
	private NavajoIOConfig config;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseContextImpl.class);
	
	public void activate() {
		logger.debug("Activating article context");
	}
	
	public void deactivate() {
		logger.debug("Deactivating article context");
		
	}
	public void addCommand(ArticleCommand command) {
		commands.put(command.getName(), command);
	}

	public void removeCommand(ArticleCommand command) {
		commands.remove(command.getName());
	}

	@Override
	public ArticleCommand getCommand(String name) {
		return commands.get(name);
	}
	
	public File resolveArticle(String pathInfo) {
		System.err.println("path: " + pathInfo);
		String sub = pathInfo.substring(1);
		String root = getConfig().getRootPath();
		File rootFolder = new File(root);
		File articles = new File(rootFolder,"article");
		File article = new File(articles,sub+".xml");
		System.err.println("Looking for file: "+article.getAbsolutePath());
		return article;
	}

	@Override
	public void interpretArticle(File article, ArticleRuntime ac) throws IOException, ArticleException {
		XMLElement articleXml = new CaseSensitiveXMLElement();
		Reader r = null;
		try {
			r = new FileReader(article);
			articleXml.parseFromReader(r);
			ac.execute(this);
		} catch (IOException e) {
			throw e;
		} finally {
			if(r!=null) {
				try {
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public NavajoIOConfig getConfig() {
		return config;
	}

	public void setConfig(NavajoIOConfig ioConfig) {
		this.config = ioConfig;
	}

	public void clearConfig(NavajoIOConfig ioConfig) {
		this.config = null;
	}
}
