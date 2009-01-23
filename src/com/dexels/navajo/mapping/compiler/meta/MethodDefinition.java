package com.dexels.navajo.mapping.compiler.meta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.XMLElement;

@SuppressWarnings("unchecked")
public class MethodDefinition {

	private String name;
	
	private HashMap<String, ParameterDefinition> parameters = new HashMap<String, ParameterDefinition>();
	private TreeMap<Integer, XMLElement> orderedParameters = new TreeMap<Integer, XMLElement>();
	private final static Random rand = new Random(System.currentTimeMillis());
	
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
	
	private String generateParamName() {
		return "Dummy" + Math.abs(MethodDefinition.rand.nextInt());
	}
	
	public void generateCode(XMLElement in, XMLElement out, String filename) throws Exception {
		
		if ( in.getChildren().size() > 0 ) {
			throw new MetaCompileException(filename, in, "Illegal children tags defined for tag <" + in.getName() + "/>");
		}
		
		String condition = (String) in.getAttribute("condition");
		boolean hasCondition = false;
		String tempParamName = null;
		if ( condition != null && !condition.equals("condition")) {
			// Generate a temp. param to evaluate the condition expression.
			XMLElement c = new TSLElement(in, "param");
			tempParamName = generateParamName();
			c.setAttribute("name", tempParamName);
			XMLElement exp = new TSLElement(in, "expression");
			exp.setAttribute("value", condition);
			c.addChild(exp);
			out.addChild(c);
			hasCondition = true;
		}
		Iterator<String> attributes = in.enumerateAttributeNames();
		HashSet<String> required = new HashSet<String>();
		// Get all 'automatic' parameters and determine required parameters.
		Iterator<ParameterDefinition> auto = parameters.values().iterator();
		while ( auto.hasNext() )  {
			ParameterDefinition pd = auto.next();
			if ( pd.getRequired().equals("automatic") ) {
				XMLElement pdx = pd.generateCode(in, pd.getValue(), false, ( hasCondition ? "[/@" + tempParamName + "]" : null ), out, false, filename);
				orderedParameters.put(new Integer(pd.getOrder()), pdx);
			} else if ( pd.getRequired().equals("true") ) {
				required.add(pd.getName());
			}
		}
		while ( attributes.hasNext() ) {
			String attribName = attributes.next();
			String attribValue = (String) in.getAttribute(attribName);
			//System.err.println("Looking up parameterdefinition: " + attribName);
			ParameterDefinition pd = parameters.get(attribName);
			if ( pd == null && !attribName.equals("condition")) {
				throw new UnknownParameterException(getName(), attribName, in, filename );
			}
			if ( pd != null && !pd.getRequired().equals("automatic")) {
				XMLElement pdx = pd.generateCode(in, attribValue, false, ( hasCondition ? "[/@" + tempParamName + "]" : null ), out, false, filename);
				orderedParameters.put(new Integer(pd.getOrder()), pdx);
				required.remove(pd.getName());
			}
		}
		// Check if all required parameters are present.
		if ( required.size() > 0 ) {
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
