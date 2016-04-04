package com.dexels.navajo.article.command.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.APIValue;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class TableCommand implements ArticleCommand {

	private String name;

	public TableCommand() {
		// default constructor
	}

	// for testing, no need to call activate this way
	public TableCommand(String name) {
		this.name = name;
	}

	public void activate(Map<String, String> settings) {
		this.name = settings.get("command.name");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String, String> parameters, XMLElement element) throws APIException {
		String service = parameters.get("service");
		if (service == null) {
			throw new APIException("No service parameter supplied for table. We need to know which navajo you want to use.", null, APIErrorCode.InternalError);
		}
		
		Navajo navajo = runtime.getNavajo(service);
		if (navajo == null) {
			throw new APIException("Navajo: " + service + " was not found in the current runtime.", null, APIErrorCode.InternalError);
		}
		
		String path = parameters.get("path");
		if (path == null) {
			throw new APIException("No path parameter supplied. Which message do you want to listen to.", null, APIErrorCode.InternalError);
		} 

		Message message = navajo.getMessage(path);
		if (message == null) {
			throw new APIException("Message: " + path + " not found", null, APIErrorCode.InternalError);
		}
		
		runtime.setMimeType("application/json; charset=utf-8");

		ArrayNode nodes = runtime.getObjectMapper().createArrayNode();
		
		for (Message data : message.getElements()) {
			ObjectNode node = runtime.getObjectMapper().createObjectNode();
			
			for (XMLElement XMLElement : element.getChildren()) {
				final String id = XMLElement.getStringAttribute("id");
				final String type = XMLElement.getStringAttribute("type");
				final String target = XMLElement.getStringAttribute("target");
				
				if (target != null) {
					//A target is a link, they do not have navajo value.
					node.put(id, resolveTarget(target, runtime, data));
				} else {
					//We default back to the id for the propertyName if not explicit set.
					final String propertyName = XMLElement.getStringAttribute("propertyName",  id);
					Property property = data.getProperty(propertyName);
					APIValue.setValueOnNodeForType(node, id, type, property, runtime);
				}
			}
			
			nodes.add(node);
		}
		
		return nodes;	
	}

	private String resolveTarget(String target, ArticleRuntime runtime,
			Message elt) throws APIException {
		final String resolved = replaceTokens(target, elt);
		Map<String, String[]> params = runtime.getParameterMap();
		boolean paramsPresent = resolved.indexOf('?') != -1;
		StringBuffer sb = new StringBuffer(resolved);
		for (Entry<String, String[]> e : params.entrySet()) {
			if (shouldSkipParam(e.getKey()))
				continue;
			
			if (!paramsPresent) {
				sb.append('?');
				paramsPresent = true;
			} else {
				sb.append('&');
			}
			sb.append(e.getKey());
			sb.append("=");
			sb.append(e.getValue()[0]);
		}
		return sb.toString();
	}
	
	/**
	 * We do not want to include the client id or the token in the link. Since this
	 * will be send to the server and causes a possible security leak in the OAuth 
	 * protocol. 
	 * 
	 * The token should only be known on either the server or the client, never 
	 * both. The client is public but doesn't have to known on both either, so
	 * we just disable it.
	 */
	private boolean shouldSkipParam (String key) {
		return "clientId".equals(key) || "client_id".equals(key) || "token".equals(key);
	}

	private String replaceTokens(String text, Message msg) throws APIException {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(text);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {

			final String group = matcher.group(1);
			Property p = msg.getProperty(group);
			if (p == null) {
				throw new APIException(
						"Error resolving target. Referenced property: " + group
								+ " not found in message", null, APIErrorCode.InternalError);
			}

			String replacement = p.getValue();
			if (replacement != null) {
				matcher.appendReplacement(buffer, "");
				buffer.append(replacement);
			}
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs, ObjectMapper mapper) {
		ObjectNode on = mapper.createObjectNode();
		outputArgs.add(on);
		final String key = e.getStringAttribute("key");
		if(key!=null) {
			on.put("key", key);
		}
		final String highlight = e.getStringAttribute("highlight");
		if(highlight!=null) {
			on.put("highlight", highlight);
		}
		List<XMLElement> children = e.getChildrenByTagName("column");
		ArrayNode an = mapper.createArrayNode();
		on.put("columns", an);
		for (XMLElement xmlElement : children) {
			ObjectNode column = mapper.createObjectNode();
			an.add(column);
			column.put("id", xmlElement.getStringAttribute("id"));
			final String type = xmlElement.getStringAttribute("type");
			if(type!=null) {
				column.put("type", type);
			}
			final String label = xmlElement.getStringAttribute("label");
			if(label!=null) {
				column.put("label", label);
			}
			final String target = xmlElement.getStringAttribute("target");
			if(target!=null) {
				column.put("target", target);
			}
			final String hidden = xmlElement.getStringAttribute("hidden");
			if(hidden!=null) {
				column.put("hidden", hidden);
			}
		}
		return true;
	}
}
