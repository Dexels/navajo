package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.oauth.api.OAuthToken;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ServletArticleRuntimeImpl extends BaseRuntimeImpl implements ArticleRuntime {

	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private String username;
	private final StringWriter writer = new StringWriter();
	private final Map<String, String[]> parameterMap;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ServletArticleRuntimeImpl.class);
	
	public ServletArticleRuntimeImpl(HttpServletRequest req, HttpServletResponse resp, String password, String username, File article,String articleName, Map<String, String[]> parameterMap,String instance,OAuthToken t) throws IOException {
		super(articleName,article,instance,t);
		this.request = req;
		this.parameterMap = parameterMap;
		this.response = resp;
		this.username = username;
	}

	
	@Override
	public String resolveArgument(String name) throws APIException {
		final String trimmedName = name.substring(1);
		String res = request.getParameter(trimmedName);
		if(res!=null) {
			return res;
		}
		XMLElement args = article.getElementByTagName("_arguments");
		if(args==null) {
			throw new APIException("Unspecified parameter reference: "+name+". No argument data found.", null, APIErrorCode.InternalError);
		}
		List<XMLElement> lts = args.getChildren();
		for (XMLElement xmlElement : lts) {
			if(trimmedName.equals(xmlElement.getStringAttribute("name"))) {
				boolean optional = xmlElement.getBooleanAttribute("optional", "true", "false", false);
				if(!optional) {
					// not optional + no value = fail
					throw new APIException("Missing parameter not optional: " + trimmedName, null, APIErrorCode.MissingRequiredArgument);
				}
				return xmlElement.getStringAttribute("default");
			}
		}
		throw new APIException("Unspecified parameter reference: "+name+". No argument data found.", null, APIErrorCode.InternalError);
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
	public String getUsername() {
		return username;
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
