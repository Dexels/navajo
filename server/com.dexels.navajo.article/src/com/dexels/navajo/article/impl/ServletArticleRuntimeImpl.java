package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.oauth.api.Token;

public class ServletArticleRuntimeImpl extends BaseRuntimeImpl implements ArticleRuntime {

	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private String password;
	private String username;
	private final StringWriter writer = new StringWriter();
	private final Map<String, String[]> parameterMap;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ServletArticleRuntimeImpl.class);
	
	public ServletArticleRuntimeImpl(HttpServletRequest req, HttpServletResponse resp, String password, String username, File article,String articleName, Map<String, String[]> parameterMap,String instance,Token t) throws IOException {
		super(articleName,article,instance,t);
		this.request = req;
		this.parameterMap = parameterMap;
		this.response = resp;
		this.password = password;
		this.username = username;
	}

	
	@Override
	public String resolveArgument(String name) throws ArticleException {
		// TODO use optionality / default value
		final String trimmedName = name.substring(1);
		String res = request.getParameter(trimmedName);
		if(res!=null) {
			return res;
		}
		XMLElement args = article.getElementByTagName("_arguments");
		if(args==null) {
			throw new ArticleException("Unspecified parameter reference: "+name+". No argument data found.");
		}
		List<XMLElement> lts = args.getChildren();
		for (XMLElement xmlElement : lts) {
			if(trimmedName.equals(xmlElement.getStringAttribute("name"))) {
				logger.debug("Found arg: "+xmlElement);
				boolean optional = xmlElement.getBooleanAttribute("optional", "true", "false", false);
				if(!optional) {
					// not optional + no value = fail
					return null;
				}
				return xmlElement.getStringAttribute("default");
			}
		}
		throw new ArticleException("Unspecified parameter reference: "+name);
	}

	@Override
	public void setMimeType(String mime) {
		response.setContentType(mime);
	}
	@Override
	public Writer getOutputWriter() throws IOException {
		return response.getWriter();
	}
	
	@Override
	public Map<String,String[]> getParameterMap() {
		return this.parameterMap;
	}

	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
	// TODO: Stream this.
	@Override
	public void commit() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readValue(writer.toString(), JsonNode.class);
		} catch (JsonParseException e) {
			logger.error("JSON parse error: ",e);
			response.getWriter().write("Invalid JSON follows:\n");
			response.getWriter().write(writer.toString());
			return;
		}
		
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		writer.writeValue(response.getWriter(), rootNode);
	}

}
