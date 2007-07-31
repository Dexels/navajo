package com.dexels.navajo.mapping.compiler.meta;

import java.util.Enumeration;
import java.util.HashMap;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MethodDefinition {

	private String name;
	
	private HashMap<String, ParameterDefinition> parameters = new HashMap<String, ParameterDefinition>();
	
	public MethodDefinition(String name, HashMap<String, ParameterDefinition> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public void generateCode(XMLElement in, XMLElement out) {
		
		Enumeration attributes = in.enumerateAttributeNames();
		while ( attributes.hasMoreElements() ) {
			String attribName = (String) attributes.nextElement();
			String attribValue = (String) in.getAttribute(attribName);
			System.err.println("Looking up parameterdefinition: " + attribName);
			ParameterDefinition pd = parameters.get(attribName);
			if ( pd != null ) {
				pd.generateCode(attribValue, out);
			}
		}
	}

	public String getName() {
		return name;
	}
	
}
