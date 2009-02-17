package com.dexels.navajo.tipi.projectbuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.extensionmanager.ExtensionManager;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class ClientActions {
	public static List<String> checkExtensions(String repository, String extensions) {
		List<String> missing = new ArrayList<String>();
		try {
			Map<String,String> available = ExtensionManager.getExtensions(repository);
			System.err.println("Available extensions: "+available);
			StringTokenizer st = new StringTokenizer(extensions, ",");
			while (st.hasMoreTokens()) {
				String ext = st.nextToken();
				if(!available.keySet().contains(ext)) {
					missing.add(ext);
				}
			}
		} catch (IOException e1) {
			System.err.println("Can not get extensionlist");
			e1.printStackTrace();
		}
		return missing;
	}
	
	
	

	public static XMLElement getExtensionXml(String extension,String repository) throws IOException {
		try {
			URL rep = new URL(repository);
			URL projectURL = new URL(rep,extension+"/");
			URL extensionURL = new URL(projectURL,"definition.xml");
			XMLElement result = getXMLElement(extensionURL);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void downloadExtensionJars(String project, URL projectURL, XMLElement result)
			throws MalformedURLException {
		System.err.println("PRoject dir: "+projectURL);
		URL unsigned = new URL(projectURL, "lib/");

		List<XMLElement> jars = result.getElementsByTagName("jar");
		File f = new File("lib");
		f.mkdirs();
		for (XMLElement element : jars) {
			String path = element.getStringAttribute("path");
			URL jar = new URL(unsigned, path);
			System.err.println("URL: " + jar);
			try {
				downloadJar(jar,path,f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		XMLElement main = result.getElementByTagName("main");
		if(main!=null) {
			String path = main.getStringAttribute("proxyjar");
			URL jar = new URL(unsigned, path);
			try {
				downloadJar(jar,path,f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void downloadJar(URL jar, String path, File f) throws IOException {
		File file = new File(f,path);
		System.err.println("File: "+f.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(file);
		InputStream is = jar.openStream();
		copyResource(fos, is);
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
public static void buildJnlp(String projectName, String repository,  File inputPath, File outputPath) throws XMLParseException, IOException {
	XMLElement xe = new CaseSensitiveXMLElement();
	FileReader fr = new FileReader(inputPath);
	xe.parseFromReader(fr);
	XMLElement output = new CaseSensitiveXMLElement("jnlp");
	buildup(projectName, repository, xe,output);
	FileWriter fw = new FileWriter(outputPath);
	output.write(fw);
	fw.flush();
	fw.close();
}



private static void buildup(String projectName, String repository, XMLElement input, XMLElement output) {
	
	XMLElement information =  output.addTagKeyValue("information", "");
	information.addTagKeyValue("vendor",input.getStringAttribute("vendor"));
	information.addTagKeyValue("homepage",input.getStringAttribute("homepage"));
	information.addTagKeyValue("title",input.getStringAttribute("title"));
	
	output.setAttribute("codebase", repository+"/"+input.getStringAttribute("project"));
	output.setAttribute("href", projectName+".jnlp");
	XMLElement resources = output.addTagKeyValue("resources", "");
	XMLElement description = input.getElementByTagName("description");
	if(description!=null && description.getContent()!=null) {
		input.addTagKeyValue("description", description.getContent().trim());
	}

	XMLElement jars = input.getElementByTagName("jars");
	if(jars!=null) {
		for (XMLElement jar : jars.getChildren()) {
			if (jar.getName().equals("jar")) {
				String path = jar.getStringAttribute("path");
				String main = jar.getStringAttribute("main");
				XMLElement resource = new CaseSensitiveXMLElement("jar");
				resource.setAttribute("href", path);
				resource.setAttribute("main", main==null?"false":true);
				resources.addChild(resource);
			}
			if (jar.getName().equals("remotejnlp")) {
				String path = jar.getStringAttribute("path");
				String name = jar.getStringAttribute("name");
				XMLElement resource = new CaseSensitiveXMLElement("extension");
				resource.setAttribute("href", path);
				resource.setAttribute("name", name);
				resources.addChild(resource);
			}

		}
	}
	XMLElement extensions = input.getElementByTagName("extensions");
	if(extensions!=null) {
		for (XMLElement extension : extensions.getChildren()) {
		   // <extension name="jogl" href="http://download.java.net/media/jogl/builds/archive/jsr-231-webstart-current/jogl.jnlp" />
			//<extension name="jogl" href="http://www.jogl.com"/> 
			System.err.println("Copying: "+extension);
			resources.addChild(extension.copy());
		}
	}
	
	XMLElement requires = input.getElementByTagName("requires");
	if(requires!=null) {
		for (XMLElement require : requires.getChildren()) {
			String id = require.getStringAttribute("id");
			String path = require.getStringAttribute("path");
			XMLElement xe = resources.addTagKeyValue("extension", "");
			xe.setAttribute("name", id);
			xe.setAttribute("href", assemblePath(repository, path,id));
		}
	}
	output.addTagKeyValue("component-desc", "");
	output.setAttribute("spec", "1.0+");
}


private static String assemblePath(String repository, String path, String id) {
	return repository+"/"+path+"/"+id+".jnlp";
} 


protected static final void copyResource(OutputStream out, InputStream in) throws IOException {
	BufferedInputStream bin = new BufferedInputStream(in);
	BufferedOutputStream bout = new BufferedOutputStream(out);
	byte[] buffer = new byte[1024];
	int read;
	while ((read = bin.read(buffer)) > -1) {
		bout.write(buffer, 0, read);
	}
	bin.close();
	bout.flush();
	bout.close();
}

}
