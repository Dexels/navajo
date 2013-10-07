package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.util.XMLElement;

public class ComponentMerger {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ComponentMerger.class);
	
	public static XMLElement getAssembledClassDef(Map<String,XMLElement> allComponents,XMLElement classDef) throws IOException  {
		
		XMLElement result = null;
		Stack<String> extensionStack = new Stack<String>();
		 appendInterfacesToClassdef(extensionStack,allComponents, classDef);
		 List<XMLElement> interfaces = getClassDefsFromStack(extensionStack,allComponents);
		if(interfaces==null) {
			result = classDef;
		} else {
			interfaces.add(classDef);
			result = assembleClassDefs(interfaces);
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
					logger.info("WARNING: missing component: "+currentName);
				}
				if(element==null) {
					throw new IOException("Error: ClassDef: "+classDef.getStringAttribute("name")+" has an unknown super interface: "+currentName);
				}
				appendInterfacesToClassdef(isExtending, allComponents, element);
				isExtending.push(element.getStringAttribute("name"));
			}
		}
	}
	
	private static XMLElement assembleClassDefs(List<XMLElement> interfaces) {
		assert (interfaces!=null);
		assert (interfaces.size()>0);
		if(interfaces.size()==1) {
			// maybe copy?
			return interfaces.get(0);
		}
	
		
		ClassModel cl = new ClassModel();
		for (XMLElement element : interfaces) {
			cl.addDefinition(element);
		}
		return cl.buildResult();
	}
	
}
