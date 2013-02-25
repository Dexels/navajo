package com.dexels.navajo.article.command.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.command.ArticleCommand;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

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
	public boolean execute(ArticleRuntime runtime, ArticleContext context, Map<String,String> parameters) throws ArticleException {
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
		Property p = current.getProperty(name);
		if(p==null) {
			current.write(System.err);
			throw new ArticleException("No property: "+name+" found in current navajo.");
		}
//		boolean writeLabel = "true".equals(parameters.get("showlabel"));
//		if(writeLabel) {
//			
//		}
		try {
			printElementJSONTypeless(p, runtime.getOutputWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void printElementJSONTypeless(Property p, final Writer sw) throws NavajoException, IOException {
		String value = p.getValue();
		if(p.getType().equals(Property.SELECTION_PROPERTY)){
			Selection s = p.getSelected();
			if(s != null){
				value = s.getValue();
			}
		}
		
		sw.write( "\"" + p.getFullPropertyName() + "\" : \"" + value + "\"");		
	}

}
