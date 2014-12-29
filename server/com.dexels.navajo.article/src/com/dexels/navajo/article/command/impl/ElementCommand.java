package com.dexels.navajo.article.command.impl;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import sun.org.mozilla.javascript.internal.ast.ForInLoop;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.DirectOutputThrowable;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;

public class ElementCommand implements ArticleCommand {

	private String name;
	
	
	public ElementCommand() {
		// default constructor
	}
	
	// for testing, no need to call activate this way
	public ElementCommand(String name) {
		this.name = name;
	}
	
	public void activate(Map<String,String> settings) {
		this.name = settings.get("command.name");
	}
	@Override
	public String getName() {
		return name;
	}

//    <element service="clubsites/nl/adresboek" name="parameters/achternaam" showlabel="true"/>

	
	@Override
	public JsonNode execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters, XMLElement element) throws ArticleException, DirectOutputThrowable {
		String service = parameters.get("service");
		Navajo current = null;
		if(service==null) {
			current = runtime.getNavajo();
		} else {
			current = runtime.getNavajo(service);
		}
		if(current==null) {
			throw new ArticleException("No current navajo found.");
		}
		String name = parameters.get("name");
		if(name==null) {
			throw new ArticleException("No 'name' parameter found in element. This is required.");
		}
		String propertyName = parameters.get("propertyName");
		if(propertyName==null) {
			propertyName = name;
		}

		Property p = current.getProperty(propertyName);
		if(p==null) {
			current.write(System.err);
			throw new ArticleException("No property: "+propertyName+" found in current navajo.");
		}
		
		if(parameters.get("direct")!=null) {
			Object value = p.getTypedValue();
			if (value instanceof Binary) {
				Binary b = (Binary)value;
				String mime = b.getMimeType();
				if(mime==null) {
					mime = b.guessContentType();
				}
				throw new DirectOutputThrowable(mime,b.getDataAsStream());
			} else {
				String string = ""+value;
				ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
				throw new DirectOutputThrowable("text/plain",bais);
			}
		}
		
		if(name.indexOf('/')!=-1) {
			String msgpath = name.substring(0, name.lastIndexOf('/'));
			String propname = name.substring(name.lastIndexOf('/')+1,name.length());
			ObjectNode msgNode = runtime.getGroupNode(msgpath);
			msgNode.put(propname, p.getValue());
			return null;
		} else {
			ObjectNode on = runtime.getRootNode();
			on.put(name, p.getValue());
			return on;
		}
	}
	


	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs, ObjectMapper mapper) {
		ObjectNode root = mapper.createObjectNode();
		String name = e.getAttribute("name").toString();
		
		//Remove the first slash if present
		if (name.charAt(0) == '/') {
			name = name.substring(1);
		}
		
		String objects[] = name.split("/");
		ObjectNode object = mapper.createObjectNode();
		if (objects.length > 1) { //Is the name constructed in levels: "voorzitter/naam"
			
			ObjectNode previous = object;
			String fieldName = "default";
			Boolean isDefined = false;
			
			//Loop through the string
			for(String string : objects) {
				if (!string.isEmpty()) {
					
					//Need the actual fieldName
					if (objects[0].equals(string)) { //First
						fieldName = string;
						
						//If the fieldName is allready defined we take that node
						JsonNode node = getNodeByFieldName(outputArgs, fieldName);
						if (node != null) {
							previous = (ObjectNode)node;
							isDefined = true;
						}
						
						continue;
					}
					
					//If it is the last component we set the actual data
					if (objects[objects.length - 1].equals(string)) { //Last
						ObjectNode node = mapper.createObjectNode();
						fillObject(node, e, string);
						previous.put(string, node);
					} else {
						//Create a new object and assign it to previous so we get the levels effect
						ObjectNode node = mapper.createObjectNode();
						previous.put(string, node);
						previous = node;
					}
				}
			}
			if (!isDefined) {
				root.put(fieldName, object);
			} else {
				//The node was already defined so we do not need to create a wrapper object
				return false;
			}	
		} else { 
			fillObject(object, e, objects[0]);
			root.put(name, object);
		}
		
		outputArgs.add(root);
		return true;
	}
	
	public void fillObject(ObjectNode node, XMLElement element, String label) {
		node.put("label", element.getAttribute("label", label).toString());
		if (element.getAttribute("type") != null) {
			node.put("type", element.getAttribute("type").toString());
		}
		if (element.getAttribute("target") != null) {
			node.put("target", element.getAttribute("target").toString());
		}
		
		if (element.getAttribute("onchange") != null) {
			node.put("onchange", element.getAttribute("onchange").toString());
		}
	}
	
	public ObjectNode getNodeByFieldName (ArrayNode nodes, String fieldName) {
		if (fieldName.length() == 0)
			return null;
		
		for (JsonNode output : nodes) {
			JsonNode node = output.path(fieldName);
			if (!node.isMissingNode()) {
				return (ObjectNode) output.path(fieldName);
			}
		}
		
		return null;
	}
}


