package com.dexels.navajo.article.command.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context,
			Map<String, String> parameters, XMLElement element)
			throws APIException {
		String service = parameters.get("service");
		if (service == null) {
			throw new APIException("No service parameter supplied for table.", null, APIErrorCode.InternalError);
		}
		String path = parameters.get("path");
		Navajo n = runtime.getNavajo(service);
		if (n == null) {
			throw new APIException("Navajo: " + service + " was not found in table command", null, APIErrorCode.InternalError);
		}
		Message m = null;
		if (path != null) {
			m = n.getMessage(path);
		}
		
		runtime.setMimeType("application/json; charset=utf-8");
		String tableName = parameters.get("name");
		if (tableName == null) {
			tableName = "data";
		}
		List<XMLElement> columnList = element.getChildren();
		List<String> columnIds = new ArrayList<String>();
		final Map<String, String> targetMap = new HashMap<String, String>();
		final Map<String, String> propertyMap = new HashMap<String, String>();
		for (XMLElement xmlElement : columnList) {
			final String id = xmlElement.getStringAttribute("id");
			final String propertyName = xmlElement.getStringAttribute("propertyName");
			if(propertyName!=null) {
				propertyMap.put(id, propertyName);
			}
			columnIds.add(id);
			
			String target = xmlElement.getStringAttribute("target");
			if (target != null) {
				targetMap.put(id, target);
			}
		}
		
		if (m == null) {
			throw new APIException("Message: " + path + " not found", null, APIErrorCode.InternalError);
		} 
			
		return writeJSON(m, tableName, runtime, columnIds, targetMap,propertyMap);
	}

	private JsonNode writeJSON(Message m, String name, ArticleRuntime runtime,
			List<String> columns, Map<String, String> targetMap, Map<String, String> propertyMap) throws APIException {
		List<Message> output = m.getElements();
		ArrayNode an = runtime.getObjectMapper().createArrayNode();
		for (Message elt : output) {
			ObjectNode on = runtime.getObjectMapper().createObjectNode();
			for (String id : columns) {
				String propertyName = propertyMap.get(id);
				if(propertyName==null) {
					propertyName = id;
				}
				String target = targetMap.get(id);
				if (target != null) {
					String resolvedTarget = resolveTarget(target, runtime, elt);
					on.put(id, resolvedTarget);
				} else {
					Property p = elt.getProperty(propertyName);
					if (p != null) {
						on.put(id, p.getValue());
					}
				}
			}
			an.add(on);
		}
		return an;
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
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,
			ObjectMapper mapper) {
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
