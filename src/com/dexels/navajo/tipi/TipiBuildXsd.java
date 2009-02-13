package com.dexels.navajo.tipi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

public class TipiBuildXsd extends BaseTipiClientTask {

	
	private final Map<String,List<XMLElement>> tipiParts = new HashMap<String, List<XMLElement>>();
	@Override
	public void execute() throws BuildException {
		File xsd = new File("tipi/tipi.xsd");
		xsd.getParentFile().mkdirs();
		
		if(extensions==null || "".equals(extensions)) {
			
			throw new BuildException("No extensions defined ");
		}
		StringTokenizer st = new StringTokenizer(extensions,",");
		while(st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
				appendExtension(ext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.err.println("eleements: "+tipiParts.keySet());

	}

	public void parseProjectDefinition(URL projectURL, XMLElement result) throws MalformedURLException, IOException {
		List<XMLElement> includes = result.getElementsByTagName("include");
		for (XMLElement element : includes) {
			String path = element.getStringAttribute("path");
			XMLElement xx = getXMLElement(new URL(projectURL,"includes/"+path));
			// beware of missing function.xml
			//System.err.println("ELEMENT: "+xx);
			if(xx!=null) {
				appendClassDefElement(xx);
			}
		}
	}

	private void appendClassDefElement(XMLElement xx) {
		// TODO Auto-generated method stub
		List<XMLElement> cc = xx.getChildren();
		for (XMLElement element : cc) {
			List<XMLElement> elts = tipiParts.get(element.getName());
			if(elts==null) {
				elts = new LinkedList<XMLElement>();
				tipiParts.put(element.getName(), elts);
			}
			elts.add(element);
		}

	}


}