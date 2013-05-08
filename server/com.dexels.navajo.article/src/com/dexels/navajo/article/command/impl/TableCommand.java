package com.dexels.navajo.article.command.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;

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
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException {
//		runtime.setMimeType("text/plain");
		String service = parameters.get("service");
		if(service==null) {
			throw new ArticleException("No service parameter supplied for table.");
		}
		String path = parameters.get("path");
//		if(path==null) {
//			throw new ArticleException("No path parameter supplied for table.");
//		}
		Navajo n = runtime.getNavajo(service);
		if(n==null) {
			throw new ArticleException("Navajo: "+service+" was not found in table command");
		}

		Message m = null;
		if(path!=null) {
			m = n.getMessage(path);
		}
//		if(m==null) {
//			n.write(System.err);
//			throw new ArticleException("Path: "+path+" was not found in navajo : "+service);
//		}
		try {
			runtime.setMimeType("text/json");
			String tableName = parameters.get("name");
			if(tableName==null) {
				tableName = "data";
			}
			List<XMLElement> columnList = element.getChildren();
			List<String> columnIds = new ArrayList<String>();
			for (XMLElement xmlElement : columnList) {
				columnIds.add(xmlElement.getStringAttribute("id"));
			}
			String columns = parameters.get("columns");

//			runtime.getOutputWriter().write("\""+tableName+"\" : ");
			if (m==null) {
				logger.warn("Ignoring table command. Message: {} not found. Dumping all.",path);
				n.writeJSONTypeless(runtime.getOutputWriter());
			} else {
				return writeJSON(m,tableName, runtime,columnIds);
			}
//			appendMetadata(runtime,tableName,columnsArray,columnLabelsArray,columnWidthsArray,parameters.get("key"),parameters.get("link"));
			
		} catch (IOException e) {
			throw new ArticleException("Error writing result", e);
		}
		return null;
//		m.write(System.err);
	}

	private JsonNode writeJSON(Message m, String name, ArticleRuntime runtime, List<String> columns) throws IOException {
		//m.writeSimpleJSON(name,runtime.getOutputWriter(),columns);
		// assume array for now
		List<Message> output = m.getElements();
		ArrayNode an = runtime.getObjectMapper().createArrayNode();
		for (Message elt : output) {
			ObjectNode on = runtime.getObjectMapper().createObjectNode();
			for (String id : columns) {
				Property p = elt.getProperty(id);
				if(p!=null) {
					on.put(id, p.getValue());
				}
			}
			an.add(on);
		}
		return an;
	}
//	private void appendMetadata(ArticleRuntime runtime, String name, String[] columns, String[] columnLabels,
//			String[] columnWidths, String key, String link) {
//		ObjectMapper om = new ObjectMapper();
//		ObjectNode root = runtime.getMetadataRootNode();
//		ObjectNode tbl = om.createObjectNode();
//		root.put(name, tbl);
//		if(columns==null) {
//			return;
//		}
//		int i = 0;
//		for (String column : columns) {
//			ObjectNode columnNode = om.createObjectNode();
//			tbl.put(column, columnNode);
//			if(columnLabels!=null) {
//				columnNode.put("description", columnLabels[i]);
//			}
//			i++;
//		}
//	}

	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
//		<column label=\"Datum\" type=\"date\" id=\"datum\"/>
		ObjectNode on = mapper.createObjectNode();
		outputArgs.add(on);
		on.put("key", e.getStringAttribute("key"));
		List<XMLElement> children = e.getChildrenByTagName("column");
		ArrayNode an = mapper.createArrayNode();
		on.put("columns", an);
		for (XMLElement xmlElement : children) {
			ObjectNode column = mapper.createObjectNode();
			an.add(column);
			column.put("id",xmlElement.getStringAttribute("id"));
			column.put("type",xmlElement.getStringAttribute("type"));
			column.put("label",xmlElement.getStringAttribute("label"));
		}
		return true;
	}

}
