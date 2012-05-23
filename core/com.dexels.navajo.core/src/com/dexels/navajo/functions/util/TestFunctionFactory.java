package com.dexels.navajo.functions.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import navajo.ExtensionDefinition;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class TestFunctionFactory extends FunctionFactoryInterface {

	public void init() {
		
		// Read config file.
		CaseSensitiveXMLElement xml = new CaseSensitiveXMLElement();
		try {
			FileInputStream fis = new FileInputStream("/home/arjen/projecten/NavajoFunctions/functions.xml");
			xml.parseFromStream(fis);
			fis.close();
			HashMap<String, FunctionDefinition> fuds = new HashMap<String, FunctionDefinition>();
			Vector<XMLElement> children = xml.getChildren();
			for (int i = 0; i < children.size(); i++) {
				// Get object, usage and description.
				XMLElement function = children.get(i);
				Vector<XMLElement> def = function.getChildren();
				String name = (String) function.getAttribute("name");
				String object = (String) function.getAttribute("class");
				String description = null;
				String inputParams = null;
				String resultParam = null;
				for (int j = 0; j < def.size(); j++) {
					
					if ( def.get(j).getName().equals("description")) {
						description =  def.get(j).getContent();
					}
					if ( def.get(j).getName().equals("input")) {
						inputParams =  def.get(j).getContent();
					}
					if ( def.get(j).getName().equals("result")) {
						resultParam =  def.get(j).getContent();
					}
				}
				if ( name != null ) {
					fuds.put(name, new FunctionDefinition(object, description, inputParams, resultParam,null));
				}
			}
			setDefaultConfig(fuds);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public void readDefinitionFile(Map<String, FunctionDefinition> fuds,
			ExtensionDefinition fd) {
		// TODO Auto-generated method stub
		
	}

}
