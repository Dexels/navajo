package com.dexels.navajo.article.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLDecoder;
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

public class ServletArticleRuntimeImpl extends BaseRuntimeImpl implements ArticleRuntime {

	
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private String token = null;
	private String username;
	private final StringWriter writer = new StringWriter();
	private Map<String, String[]> parameterMap; 
	
	private final static Logger logger = LoggerFactory
			.getLogger(ServletArticleRuntimeImpl.class);
	
	public ServletArticleRuntimeImpl(HttpServletRequest req, HttpServletResponse resp, File article,String articleName, Map<String, String[]> parameterMap) throws IOException {
		super(articleName,article);
		this.request = req;
		this.parameterMap = parameterMap;
		this.response = resp;
		this.token = URLDecoder.decode(req.getParameter("token"),"UTF-8");
		token=token.replaceAll(" ", "+");
		this.username = req.getParameter("username");
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
//		return writer;
	}
	
	@Override
	public Map<String,String[]> getParameterMap() {
		return this.parameterMap;
	}

	
	@Override
	public String getPassword() {
		return token;
	}
	
	@Override
	public String getUsername() {
		return username;
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
//		IOUtils.copy(new StringReader(writer.toString()), response.getWriter());
	}

}
