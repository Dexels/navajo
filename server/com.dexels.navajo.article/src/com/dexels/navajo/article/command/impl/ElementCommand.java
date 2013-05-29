package com.dexels.navajo.article.command.impl;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

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
			ObjectNode on = runtime.getObjectMapper().createObjectNode();
			on.put(name, p.getValue());
			return on;
		}
	}
	


	@Override
	public boolean writeMetadata(XMLElement e, ArrayNode outputArgs,ObjectMapper mapper) {
		return false;
	}

}
