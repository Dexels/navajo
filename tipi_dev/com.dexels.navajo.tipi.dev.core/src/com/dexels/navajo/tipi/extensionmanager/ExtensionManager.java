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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class ExtensionManager {
	/**
	 * Add the current version of the extension to the repository It parses the
	 * current extension file. It appends the new extension It writes back the
	 * extension file, ready for upload
	 * 
	 * @param extension
	 * @param repository
	 * @param path
	 * @param version
	 */
	public static void registerExtension(String extension, String repository, File path, String version) {
		System.err.println("Registering extension: " + extension + " to repository: " + repository);
		Map<String, List<String>> extensions = null;
		try {
			extensions = getExtensions(repository);
		} catch (IOException e) {
			System.err.println("No extensions found... Assuming this is the first");
			extensions = new HashMap<String, List<String>>();
		}
		List<String> versions = extensions.get(extension);
		if (versions == null) {
			versions = new LinkedList<String>();
			extensions.put(extension, versions);
		}
		if(!versions.contains(version)) {
			versions.add(version);			
		}
		File extensionFile = new File(path, "extensions.xml");
		System.err.println("Writing extensions to path: " + extensionFile);
		System.err.println("Extensions: " + extensions);
		XMLElement xe = createXML(extensions);
		try {
			xe.writeToFile(extensionFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static XMLElement createXML(Map<String, List<String>> extensions) {
		XMLElement result = new CaseSensitiveXMLElement("extensions");
		for (Entry<String, List<String>> entry : extensions.entrySet()) {
			if (entry.getValue() != null) {
				for (String version : entry.getValue()) {
					XMLElement child = new CaseSensitiveXMLElement("extension");
					result.addChild(child);
					child.setAttribute("name", entry.getKey());
					child.setAttribute("version", version);
				}

			}
		}
		return result;
	}

	private static XMLElement downloadExtensions(String extensionRepository) throws IOException {
		URL rep = new URL(extensionRepository);
		URL u = new URL(rep, "extensions.xml");
		XMLElement xe = getXMLElement(u);
		return xe;
	}

//	private static XMLElement downloadUrl(URL u) throws IOException {
//		InputStream is = u.openStream();
//		Reader r = new InputStreamReader(is);
//		XMLElement xe = new CaseSensitiveXMLElement();
//		xe.parseFromReader(r);
//		is.close();
//		return xe;
//	}

	public static Map<String, List<String>> getExtensions(String extensionRepository) throws IOException {
		System.err.println("Getting extensions from repository: "+extensionRepository);
		XMLElement xe;
		try {
			xe = downloadExtensions(extensionRepository);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Error downloading from repository: "+extensionRepository);
		}
		List<XMLElement> children = xe.getChildren();
		Map<String, List<String>> result = new TreeMap<String, List<String>>();
		for (XMLElement element : children) {
			String name = element.getStringAttribute("name");
			List<String> versions = result.get(name);
			if (versions == null) {
				versions = new LinkedList<String>();
				result.put(name, versions);
			}
			String version = element.getStringAttribute("version");
			if(!versions.contains(version)) {
				versions.add(version);
			}
			}
		// Collections.sort(result);
		return result;
	}

	public static Map<String, String> getMain(String repository, String mainExtension,String version) throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			URL rep = new URL(repository + mainExtension +"/"+version+ "/definition.xml");
			XMLElement xe = getXMLElement(rep);
			XMLElement main = xe.getElementByTagName("main");
			if (main != null) {
				result.put("class", main.getStringAttribute("class"));
				result.put("href", main.getStringAttribute("proxyjar"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean isMainExtension(String repository, String ext,String version) throws IOException {
		try {
			URL rep = new URL(repository + ext + "/"+version+"/definition.xml");
			XMLElement xe = getXMLElement(rep);
			XMLElement main = xe.getElementByTagName("main");
			if (main != null) {
				return true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Beware, mainJarList is a classic call by ref var. Pass (for example) an
	 * empty map
	 * 
	 * @param repository
	 * @param ext
	 * @param mainJarList
	 * @return
	 * @throws IOException 
	 */
	public static List<String> getJars(String repository, String ext, String version, Map<String, String> mainJarList,
			Map<String, String> extensionMap) throws IOException {
		List<String> result = new ArrayList<String>();
		try {
			URL rep = new URL(repository + ext +"/"+version+ "/definition.xml");
			XMLElement xe = getXMLElement(rep);
			XMLElement main = xe.getElementByTagName("jars");
			if (main == null) {
				return result;
			}
			for (XMLElement element : main.getChildren()) {
				if (element.getName().equals("jar")) {
					result.add(element.getStringAttribute("path"));
					String mainClass = element.getStringAttribute("main");
					if (mainClass != null) {
						mainJarList.put(element.getStringAttribute("path"), mainClass);
					}
				}
				if (element.getName().equals("remotejnlp")) {
					extensionMap.put(element.getStringAttribute("name"), element.getStringAttribute("path"));

				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static XMLElement getXMLElement(URL extensionURL) throws IOException {
		try {
			System.err.println("Downloading (EM): "+extensionURL);
			InputStream is = ClientActions.getUrlStream(extensionURL);
			XMLElement result = new CaseSensitiveXMLElement();
//			InputStream is = extensionURL.openStream();
			Reader r = new InputStreamReader(is);
			result.parseFromReader(r);
			is.close();
			r.close();
			return result;
		} catch (XMLParseException e) {
			e.printStackTrace();
		} 
		return null;
	}

}
