package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.oauth.api.OAuthToken;
import com.dexels.oauth.api.TokenStore;
import com.dexels.oauth.api.exception.TokenStoreException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class BaseContextImpl implements ArticleContext {

	private final Map<String, ArticleCommand> commands = new HashMap<String, ArticleCommand>();
	private NavajoIOConfig config;
	private TokenStore tokenStore;

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
	
	public void setTokenStore(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	public void clearTokenStore(TokenStore tokenStore) {
		this.tokenStore = null;
	}

	@Override
	public ArticleCommand getCommand(String name) {
		return commands.get(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> getScopes(String token) throws TokenStoreException {
		Map<String, Object> result = new HashMap<String, Object>();
		OAuthToken t = tokenStore.getToken(token);

		if (token != null) {
			result.putAll(t.getAttributes());
			result.put("clientId", t.getClientId());
		}
		
		return result;
	}

	@Override
	public List<String> listArticles() {
		return listArticles(false);
	}

	protected List<String> listArticles(boolean filterArticlesWithArguments) {
		String root = getConfig().getRootPath();
		File rootFolder = new File(root);
		File articles = new File(rootFolder, "article");
		String[] list = articles.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});
		List<String> result = new ArrayList<String>();
		for (String elt : list) {
			if (filterArticlesWithArguments) {
				Map<String, String> arguments = getArticleArguments(new File(
						articles, elt));
				if (arguments.isEmpty()) {
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
		Map<String, String> result = new HashMap<String, String>();
		try {
			fr = new FileReader(file);
			XMLElement x = new CaseSensitiveXMLElement();
			x.parseFromReader(fr);
			final Iterator<String> enumerateAttributeNames = x
					.enumerateAttributeNames();
			while (enumerateAttributeNames.hasNext()) {
				String attr = enumerateAttributeNames.next();
				result.put(attr, x.getStringAttribute(attr));
			}
		} catch (IOException e) {
			logger.error("Problem parsing article: ", e);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
		}

		return result;
	}

	@Override
	public File resolveArticle(String pathInfo) {
		String sub;
		if(pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		if(pathInfo.indexOf("/")!=-1) {
			sub = pathInfo.substring(pathInfo.lastIndexOf("/"), pathInfo.length());
		} else {
			sub = pathInfo;
		}

		String root = getConfig().getRootPath();
		File rootFolder = new File(root);
		File articles = new File(rootFolder, "article");
		File article = new File(articles, sub + ".xml");
		return article;
	}

	@Override
	public void interpretArticle(File article, ArticleRuntime ac) throws APIException, NoJSONOutputException {
		XMLElement articleXml = new CaseSensitiveXMLElement();
		Reader r = null;
		try {
			r = new FileReader(article);
			articleXml.parseFromReader(r);
			ac.execute(this);
		} catch (IOException e) {
			throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}
		}
	}
	

	@Override
	public void writeArticleMeta(String name, ObjectNode w, ObjectMapper mapper, boolean extended) throws APIException {
		File in = resolveArticle(name);
		
		try (FileReader fr = new FileReader(in)) {
			ObjectNode article = mapper.createObjectNode();
			w.set(name, article);
			XMLElement x = new CaseSensitiveXMLElement();
			x.parseFromReader(fr);
			article.put("name", name);
			interpretMeta(x, mapper, article, extended);
		} catch (FileNotFoundException e) {
			throw new APIException("Article " + name + " not found", e, APIErrorCode.ArticleNotFound);
		} catch (XMLParseException e) {
			throw new APIException("Article " + name + " is not valid XML", e, APIErrorCode.InternalError);
		} catch (IOException e) {
			throw new APIException("Autoclose on filereader failed", e, APIErrorCode.InternalError);
		}
	}

	public void interpretMeta(XMLElement article, ObjectMapper mapper, ObjectNode articleNode, boolean extended) throws APIException {
		String outputType = article.getStringAttribute("output");
		if (outputType != null) {
			articleNode.put("output", outputType);
		}
		String scopes = article.getStringAttribute("scopes");
		if (scopes != null) {
			String[] scopeArray = scopes.split(",");
			ArrayNode scopeArgs = mapper.createArrayNode();
			for (String scope : scopeArray) {
				scopeArgs.add(scope);
			}
			articleNode.set("scopes", scopeArgs);
		}
		
		String description = article.getStringAttribute("description");
		if (extended && description != null && description.length() != 0) {
			articleNode.put("description", description);
		}
		
		XMLElement argTag = article.getChildByTagName("_arguments");
		ArrayNode inputArgs = mapper.createArrayNode();
		articleNode.set("input", inputArgs);
		if (argTag != null) {
			List<XMLElement> args = argTag.getChildren();
			for (XMLElement xmlElement : args) {
				// name="aantalregels" description="Maximum aantal regels"
				// type="integer" optional="true" default="5"
				ObjectNode input = mapper.createObjectNode();
				input.put("name", xmlElement.getStringAttribute("name"));
				input.put("description",
						xmlElement.getStringAttribute("description"));
				input.put("type", xmlElement.getStringAttribute("type"));
				final boolean optional = xmlElement.getBooleanAttribute(
						"optional", "true", "false", false);
				input.put("optional", optional);

				final String defaultValue = xmlElement
						.getStringAttribute("default");
				if (defaultValue != null) {
					input.put("default", defaultValue);
				}
				final String sourcearticle = xmlElement
						.getStringAttribute("sourcearticle");
				if (sourcearticle != null) {
					input.put("sourcearticle", sourcearticle);
				}
				final String sourcekey = xmlElement
						.getStringAttribute("sourcekey");
				if (sourcekey != null) {
					input.put("sourcekey", sourcekey);
				}
				inputArgs.add(input);
			}
		}
		ArrayNode outputArgs = mapper.createArrayNode();
		articleNode.set("output", outputArgs);
		List<XMLElement> children = article.getChildren();

		for (XMLElement e : children) {
			String name = e.getName();
			if (name.startsWith("_")) {
				continue;
			}
			ArticleCommand ac = getCommand(name);
			if (ac == null) {
				throw new APIException("Unknown command: " + name, null, APIErrorCode.InternalError);
			}
			Map<String, String> parameters = new HashMap<String, String>();

			for (Iterator<String> iterator = e.enumerateAttributeNames(); iterator
					.hasNext();) {
				String attributeName = iterator.next();
				parameters.put(attributeName,
						e.getStringAttribute(attributeName));
			}
			
			ac.writeMetadata(e, outputArgs, mapper);
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



	public static void main(String[] args) {
		
		
	}
}
