package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
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

	@Override
	public List<String> listArticles() {
		return listArticles(false);
	}

	protected List<String> listArticles(boolean filterArticlesWithArguments) {
		String root = getConfig().getRootPath();
		File rootFolder = new File(root);
		File articles = new File(rootFolder,"article");
		String[] list = articles.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		List<String> result = new ArrayList<String>();
		for (String elt : list) {
			if(filterArticlesWithArguments) {
				Map<String,String> arguments = getArticleArguments(new File(articles,elt));
				if(arguments.isEmpty()) {
					result.add(elt.substring(0, elt.lastIndexOf('.')));
				}
			} else {
				result.add(elt.substring(0, elt.lastIndexOf('.')));
			}
			
		}
		return result;
	}

	
	private Map<String, String> getArticleArguments(File file) {
		FileReader fr = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			fr = new FileReader(file);
			XMLElement x = new CaseSensitiveXMLElement();
			x.parseFromReader(fr);
			final Iterator<String> enumerateAttributeNames = x.enumerateAttributeNames();
			while(enumerateAttributeNames.hasNext()) {
				String attr = enumerateAttributeNames.next();
				result.put(attr, x.getStringAttribute(attr));
			}
		} catch (IOException e) {
			logger.error("Problem parsing article: ", e);
		} finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
		}
		
		return result;
	}

	public File resolveArticle(String pathInfo) {
		String sub;
		if(pathInfo.startsWith("/")) {
			sub = pathInfo.substring(1);
		} else {
			sub = pathInfo;
		}
		
		String root = getConfig().getRootPath();
		File rootFolder = new File(root);
		File articles = new File(rootFolder,"article");
		File article = new File(articles,sub+".xml");
		System.err.println("Looking for file: "+article.getAbsolutePath());
		return article;
	}

	@Override
	public void interpretArticle(File article, ArticleRuntime ac) throws IOException, ArticleException, DirectOutputThrowable {
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
					logger.error("Error: ", e);
				}
			}
		}
	}
	
	public void interpretMeta(XMLElement article, ObjectMapper mapper, ObjectNode articleNode) throws ArticleException {
		int i = 0;
		
		String outputType = article.getStringAttribute("output");
		if(outputType!=null) {
			articleNode.put("output", outputType);
		}
			XMLElement argTag = article.getChildByTagName("_arguments");
			ArrayNode inputArgs = mapper.createArrayNode();
			articleNode.put("input", inputArgs);
			if(argTag!=null) {
				List<XMLElement> args = argTag.getChildren();
				for (XMLElement xmlElement : args) {
//					name="aantalregels" description="Maximum aantal regels" type="integer" optional="true" default="5"
					ObjectNode input = mapper.createObjectNode();
					input.put("name", xmlElement.getStringAttribute("name"));
					input.put("description", xmlElement.getStringAttribute("description"));
					input.put("type", xmlElement.getStringAttribute("type"));
					final boolean optional = xmlElement.getBooleanAttribute("optional", "true", "false", false);
					input.put("optional", optional);
					
					final String defaultValue = xmlElement.getStringAttribute("default");
					if(defaultValue!=null) {
						input.put("default", defaultValue);
					}
					final String sourcearticle = xmlElement.getStringAttribute("sourcearticle");
					if (sourcearticle!=null) {
						input.put("sourcearticle", sourcearticle);
					}
					final String sourcekey = xmlElement.getStringAttribute("sourcekey");
					if(sourcekey!=null) {
						input.put("sourcekey", sourcekey);
					}
					inputArgs.add(input);
				}
			}
			ArrayNode outputArgs = mapper.createArrayNode();
			articleNode.put("output", outputArgs);
			List<XMLElement> children = article.getChildren();
			
			for (XMLElement e : children) {
				String name = e.getName();
				if(name.startsWith("_")) {
					continue;
				}
				ArticleCommand ac = getCommand(name);
				if(ac==null) {
					throw new ArticleException("Unknown command: "+name);
				}
				Map<String,String> parameters = new HashMap<String, String>();
				 
				for (Iterator<String> iterator = e.enumerateAttributeNames(); iterator.hasNext();) {
					String attributeName = iterator.next();
					parameters.put(attributeName, e.getStringAttribute(attributeName));
				}
				System.err.println("Calling command # "+(i++));
				if(ac.writeMetadata(e,outputArgs,mapper)) {
				}

			}
	}

	@Override
	public void writeArticleMeta(String name,ObjectNode w, ObjectMapper mapper) throws ArticleException {
		File in = resolveArticle(name);
		FileReader fr = null;
		try {
			ObjectNode article = mapper.createObjectNode();
			w.put(name, article);
			fr = new FileReader(in);
			XMLElement x = new CaseSensitiveXMLElement();
			x.parseFromReader(fr);
			article.put("name", name);
			interpretMeta(x, mapper,article);
			System.err.println("x:\n"+x);
		} catch (IOException e) {
			logger.error("Problem parsing article: ", e);
		} finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
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
