package com.dexels.navajo.mapping.compiler.meta;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MethodDefinition {

	private String name;
	
	private HashMap<String, ParameterDefinition> parameters = new HashMap<String, ParameterDefinition>();
	private TreeMap<Integer, XMLElement> orderedParameters = new TreeMap<Integer, XMLElement>();
	
	public MethodDefinition(String name, HashMap<String, ParameterDefinition> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public static MethodDefinition parseDef(XMLElement e) {
		
		HashMap<String, ParameterDefinition> params = new HashMap<String, ParameterDefinition>();
		
		String name = (String) e.getAttribute("name");
		Vector paramV = e.getChildren();
		for ( int i = 0; i < paramV.size(); i++ ) {
			XMLElement c = (XMLElement) paramV.get(i);
			ParameterDefinition pd = ParameterDefinition.parseDef(c, i);
			params.put(pd.getName(), pd);
		}
		MethodDefinition md = new MethodDefinition(name, params);
		
		return md;
	}
	
	public void generateCode(XMLElement in, XMLElement out, String filename) throws Exception {
		
		if ( in.getChildren().size() > 0 ) {
			throw new MetaCompileException(filename, in.getLineNr(), "Illegal children tags defined for tag <" + in.getName() + "/>");
		}
		
		String condition = (String) in.getAttribute("condition");
		if ( condition != null && !condition.equals("condition")) {
			XMLElement c = new CaseSensitiveXMLElement();
			c.setName("message");
			c.setAttribute("name", "__dummy__");
			c.setAttribute("condition", condition);
			c.setAttribute("mode", "ignore");
			out.addChild(c);
			out = c;
		}
		Enumeration attributes = in.enumerateAttributeNames();
		HashSet<String> required = new HashSet<String>();
		// Get all 'automatic' parameters and determine required parameters.
		Iterator<ParameterDefinition> auto = parameters.values().iterator();
		while ( auto.hasNext() )  {
			ParameterDefinition pd = auto.next();
			if ( pd.getRequired().equals("automatic") ) {
				XMLElement pdx = pd.generateCode(pd.getValue(), null, out, false, filename);
				orderedParameters.put(new Integer(pd.getOrder()), pdx);
			} else if ( pd.getRequired().equals("true") ) {
				required.add(pd.getName());
			}
		}
		while ( attributes.hasMoreElements() ) {
			String attribName = (String) attributes.nextElement();
			String attribValue = (String) in.getAttribute(attribName);
			//System.err.println("Looking up parameterdefinition: " + attribName);
			ParameterDefinition pd = parameters.get(attribName);
			if ( pd == null && !attribName.equals("condition")) {
				throw new UnknownParameterException(getName(), attribName, in.getLineNr(), filename );
			}
			if ( pd != null && !pd.getRequired().equals("automatic")) {
				XMLElement pdx = pd.generateCode(attribValue, null, out, false, filename);
				orderedParameters.put(new Integer(pd.getOrder()), pdx);
				required.remove(pd.getName());
			}
		}
		// Check if all required parameters are present.
		if ( required.size() > 0 ) {
			throw new MissingParameterException(required, getName(), in.getLineNr(), filename );
		}
		Iterator<XMLElement> iter = orderedParameters.values().iterator();
		while ( iter.hasNext() ) {
			XMLElement xe = iter.next();
			out.addChild(xe);
		}
	}

	public String getName() {
		return name;
	}
	
}
