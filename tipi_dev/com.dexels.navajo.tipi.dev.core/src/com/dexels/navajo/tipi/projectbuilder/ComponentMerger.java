package com.dexels.navajo.tipi.projectbuilder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.util.XMLElement;

public class ComponentMerger {
	
	//	public static XMLElement mergeComponents(XMLElement base, XMLElement descendant) {
//		XMLElement result = base.copy();
//		
//		return result;
//	}
//	
//	
//	public static XMLElement mergeWithBaseElements(Map<String,XMLElement> allComponents, XMLElement element) {
//		String baseComponents = element.getStringAttribute("implements");
//		if(baseComponents==null) {
//			return element;
//		}
//		String[] baseC = baseComponents.split(",");
//		for (int i = 0; i < baseC.length; i++) {
//			XMLElement base = allComponents.get(baseC[i]);
//			element = mergeComponents(base, element);
//		}
//	   return element;
//	}
//	 <tipiclass addtocontainer="true" childcount="*" class="TipiWindow" implements="basecomponent,datacomponent,swingcomponent" layoutmanager="true" module="container" name="window" package="com.dexels.navajo.tipi.components.swingimpl"
	public static XMLElement getAssembledClassDef(Map<String,XMLElement> allComponents,XMLElement classDef,String name) throws IOException  {
		
		XMLElement result = null;
		Stack<String> extensionStack = new Stack<String>();
		 appendInterfacesToClassdef(extensionStack,allComponents, classDef);
		 List<XMLElement> interfaces = getClassDefsFromStack(extensionStack,allComponents);
		if(interfaces==null) {
			result = classDef;
		} else {
			interfaces.add(classDef);
			result = assembleClassDefs(interfaces,name);
		}
		return result;
	}

	private static List<XMLElement> getClassDefsFromStack(Stack<String> extensionStack, Map<String, XMLElement> allComponents) {
		List<XMLElement> result = new LinkedList<XMLElement>();
		for (String string : extensionStack) {
			result.add(allComponents.get(string));
		}
		return result;
	}

	private static void appendInterfacesToClassdef(Stack<String> isExtending, Map<String,XMLElement> allComponents,XMLElement classDef) throws IOException {
		String extending = classDef.getStringAttribute("implements");
		if(extending!=null) {
			StringTokenizer st = new StringTokenizer(extending,",");
//			LinkedList<XMLElement> isExtending = new LinkedList<XMLElement>();
			while(st.hasMoreTokens()) {
				String currentName = st.nextToken();
				XMLElement element = allComponents.get(currentName);
				if(element==null) {
					System.err.println("WARNING: missing component: "+currentName);
				}
				appendInterfacesToClassdef(isExtending, allComponents, element);
				if(element==null) {
					throw new IOException("Error: ClassDef: "+classDef.getStringAttribute("name")+" has an unknown super interface: "+currentName);
				}
				isExtending.push(element.getStringAttribute("name"));
			}
		}
	}
	
	private static XMLElement assembleClassDefs(List<XMLElement> interfaces,String name) {
		assert (interfaces!=null);
		assert (interfaces.size()>0);
		if(interfaces.size()==1) {
			// maybe copy?
			return interfaces.get(0);
		}
	
		
		ClassModel cl = new ClassModel(name);
		for (XMLElement element : interfaces) {
			cl.addDefinition(element);
		}
		return cl.buildResult();
	}
	
}
