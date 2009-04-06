package com.dexels.navajo.tipi.extensionmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class ExtensionManager {
	/**
	 * Add the current version of the extension to the repository
	 * It parses the current extension file.
	 * It appends the new extension
	 * It writes back the extension file, ready for upload
	 * 
	 * @param extension
	 * @param repository
	 * @param path
	 * @param version
	 */
	public static void registerExtension(String extension, String repository, File path,String version) {
		System.err.println("Registering extension: "+extension+" to repository: "+repository);
		Map<String,String> extensions = null;
		try {
			extensions = getExtensions(repository);
		} catch (IOException e) {
			System.err.println("No extensions found... Assuming this is the first");
			extensions = new HashMap<String,String>();
		}
		extensions.put(extension,version);
		File extensionFile = new File(path, "extensions.xml");
		System.err.println("Writing extensions to path: "+extensionFile);
		XMLElement xe = createXML(extensions);
		try {
			xe.writeToFile(extensionFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static XMLElement createXML(Map<String,String> extensions) {
		XMLElement result = new CaseSensitiveXMLElement("extensions");
		for (Entry<String,String> entry : extensions.entrySet()) {
			XMLElement child = new CaseSensitiveXMLElement("extension");
			result.addChild(child);
			child.setAttribute("name", entry.getKey());
			if(entry.getValue()!=null) {
				child.setAttribute("version", entry.getValue());
				
			}
		}
		return result;
	}

	private static XMLElement downloadExtensions(String repository) throws IOException {
		URL rep = new URL(repository);
		URL u = new URL(rep,"extensions.xml");
		InputStream is = u.openStream();
		Reader r = new InputStreamReader(is);
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(r);
		is.close();
		return xe;
	}

	public static Map<String,String> getExtensions(String repository) throws IOException {
		XMLElement xe = downloadExtensions(repository);
		List<XMLElement> children = xe.getChildren();
		Map<String,String> result = new HashMap<String,String>();
		for (XMLElement element : children) {
			result.put(element.getStringAttribute("name"),element.getStringAttribute("version"));
		}
//		Collections.sort(result);
		return result;
	}

	public static Map<String, String> getMain(String repository,String mainExtension) {
		Map<String,String> result = new HashMap<String, String>();
		try {
			URL rep = new URL(repository+mainExtension+"/definition.xml");
			XMLElement xe = getXMLElement(rep);
			XMLElement main = xe.getElementByTagName("main");
			if(main!=null) {
				result.put("class", main.getStringAttribute("class"));
				result.put("href", main.getStringAttribute("proxyjar"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static boolean isMainExtension(String repository, String ext) {
		try {
			URL rep = new URL(repository+ext+"/definition.xml");
			XMLElement xe = getXMLElement(rep);
			XMLElement main = xe.getElementByTagName("main");
			if(main!=null) {
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Beware, mainJarList is a classic call by ref var. Pass (for example) an empty map
	 * @param repository
	 * @param ext
	 * @param mainJarList
	 * @return
	 */
	public static List<String> getJars(String repository, String ext,Map<String,String> mainJarList,Map<String,String> extensionMap) {
		List<String> result = new ArrayList<String>();
		try {
			URL rep = new URL(repository+ext+"/definition.xml");
			XMLElement xe = getXMLElement(rep);
			XMLElement main = xe.getElementByTagName("jars");
			if(main==null) {
				return result;
			}
			for (XMLElement element : main.getChildren()) {
				if (element.getName().equals("jar")) {
					result.add(element.getStringAttribute("path"));
					String mainClass = element.getStringAttribute("main");
					if (mainClass != null) {
						mainJarList.put(element.getStringAttribute("path"),
								mainClass);
					}
				}
				if (element.getName().equals("remotejnlp")) {
					extensionMap.put(element.getStringAttribute("name"), element.getStringAttribute("path"));

				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result ;
	}
	
	public static XMLElement getXMLElement(URL extensionURL)  {
		try {
			XMLElement result = new CaseSensitiveXMLElement();
			InputStream is = extensionURL.openStream();
			Reader r = new InputStreamReader(is);
			result.parseFromReader(r);
			r.close();
			is.close();
			return result;
		} catch (XMLParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
