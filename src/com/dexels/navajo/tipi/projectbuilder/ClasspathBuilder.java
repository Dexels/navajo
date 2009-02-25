package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class ClasspathBuilder {
	public void build(String repository, String extensions, File baseDir) {
		File classpath = new File(baseDir, ".classpath");
		File src = new File(baseDir,"src");
		src.mkdir();
		File bin = new File(baseDir,"bin");
		bin.mkdir();
		XMLElement output = new CaseSensitiveXMLElement();
		output.setName("classpath");
		XMLElement elt = new CaseSensitiveXMLElement("classpathentry");
		output.addChild(elt);
		elt.setAttribute("kind", "con");
		elt.setAttribute("path", "org.eclipse.jdt.launching.JRE_CONTAINER");

		elt = new CaseSensitiveXMLElement("classpathentry");
		output.addChild(elt);
		elt.setAttribute("kind", "output");
		elt.setAttribute("path", "bin");
		
		elt = new CaseSensitiveXMLElement("classpathentry");
		output.addChild(elt);
		elt.setAttribute("kind", "src");
		elt.setAttribute("path", "src");

		
		List<String> missing = ClientActions.checkExtensions(repository,extensions);
		if(!missing.isEmpty()) {
			throw new IllegalArgumentException("Requested extension(s) not available on repository: "+missing);
		}
		StringTokenizer st = new StringTokenizer(extensions, ",");
		Set<String> alreadyPresent = new TreeSet<String>();
		while (st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
				XMLElement currentDefinition = ClientActions.getXMLElement(new URL(repository+ext+"/definition.xml"));
				appendEntry(currentDefinition,output,alreadyPresent);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
//			try {
		//		ClientActions.downloadExtensionJars(ext,repository);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
		try {
			FileWriter fw = new FileWriter(classpath);
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			output.write(fw);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendEntry(XMLElement currentDefinition, XMLElement output, Set<String> presentEntries) {
		
		
		XMLElement xee = currentDefinition.getElementByTagName("jars");
		List<XMLElement> children = xee.getChildren();

		
		for (XMLElement element : children) {
			String path = element.getStringAttribute("path");
			if(presentEntries.contains(path)) {
				continue;
			}
			XMLElement elt = new CaseSensitiveXMLElement("classpathentry");
			output.addChild(elt);
			elt.setAttribute("kind", "lib");
			elt.setAttribute("path", "lib/"+path);
			presentEntries.add(path);
		}
		
	}

}
