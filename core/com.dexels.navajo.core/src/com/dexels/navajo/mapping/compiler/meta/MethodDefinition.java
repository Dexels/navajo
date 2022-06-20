/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MethodDefinition {

	private String name;
	
	private Map<String, ParameterDefinition> parameters = new HashMap<>();
	private static final Random rand = new Random(System.currentTimeMillis());
	
	public MethodDefinition(String name, Map<String, ParameterDefinition> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public static MethodDefinition parseDef(XMLElement e) {
		
		Map<String, ParameterDefinition> params = new HashMap<>();
		
		String name = (String) e.getAttribute("name");
		Vector<XMLElement> paramV = e.getChildren();
		for ( int i = 0; i < paramV.size(); i++ ) {
			XMLElement c = paramV.get(i);
			ParameterDefinition pd = ParameterDefinition.parseDef(c, i);
			params.put(pd.getName(), pd);
		}
		return new MethodDefinition(name, params);
	}
	
	public ParameterDefinition getParameterDefinition(String name) {
		return parameters.get(name);
	}
	
	public Set<String> getParameters() {
		return parameters.keySet();
	}
	
	private String generateParamName() {
		return "Dummy" + Math.abs(MethodDefinition.rand.nextInt()+1);
	}
	
	public void generateCode(XMLElement in, XMLElement out, String filename) throws MetaCompileException {
		Map<Integer, XMLElement> orderedParameters = new TreeMap<>();
		String condition = (String) in.getAttribute("condition");
		// Create condition that always evaluates to true if an empty condition is specified.
		if ( condition == null || condition.trim().equals("") ) {
			condition = "true";
		}
		boolean hasCondition = false;
		String tempParamName = null;
		if ( !condition.equals("true")) {
			// Generate a temp. param to evaluate the condition expression.
			XMLElement c = new TSLElement(in, "param");
			tempParamName = generateParamName();
			c.setAttribute("name", Navajo.MESSAGE_SEPARATOR + tempParamName); // Force absolute param name.
			XMLElement exp = new TSLElement(in, "expression");
			exp.setAttribute("value", condition);
			c.addChild(exp);
			out.addChild(c);
			hasCondition = true;
		}
		Iterator<String> attributes = in.enumerateAttributeNames();
		Set<String> required = new HashSet<>();
		// Get all 'automatic' parameters and determine required parameters.
		Iterator<ParameterDefinition> auto = parameters.values().iterator();
		while ( auto.hasNext() )  {
			ParameterDefinition pd = auto.next();
			if ( pd.getRequired().equals("automatic") ) {
			    String setterValue = pd.getValue();
			    if (in.getAttribute(pd.getName()) != null) {
			        setterValue = (String) in.getAttribute(pd.getName());
			    }
				XMLElement pdx = pd.generateCode(in, setterValue, false, ( hasCondition ? "[/@" + tempParamName + "]" : null ), out, false, filename);
				orderedParameters.put(pd.getOrder(), pdx);
			} else if ( pd.getRequired().equals("true") ) {
				required.add(pd.getName());
			}
		}
		while ( attributes.hasNext() ) {
			String attribName = attributes.next();
			String attribValue = (String) in.getAttribute(attribName);
			ParameterDefinition pd = parameters.get(attribName);
			if ( pd == null && !attribName.equals("condition")) {
				throw new UnknownParameterException(getName(), attribName, in, filename );
			}
			if ( pd != null && !pd.getRequired().equals("automatic")) {
				XMLElement pdx = pd.generateCode(in, attribValue, false, ( hasCondition ? "[/@" + tempParamName + "]" : null ), out, false, filename);
				orderedParameters.put(pd.getOrder(), pdx);
				required.remove(pd.getName());
			}
		}
		// Check if all required parameters are present.
		if ( !required.isEmpty() ) {
			throw new MissingParameterException(required, getName(), in, filename );
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
