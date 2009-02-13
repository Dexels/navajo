package com.dexels.navajo.tipi;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;


public abstract class BaseTipiClientTask extends org.apache.tools.ant.Task {

	protected String repository = "";
	protected String extensions = "";
	
	public abstract void parseProjectDefinition(URL projectURL, XMLElement result) throws MalformedURLException, IOException;

	
	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}


		
//		File destDir = new File(getProject().getBaseDir(),".");
//		File destFile = new File(destDir,"aap.txt");
//		try {
//			XMLElement xe = appendExtension("NavajoSwingTipi", "NavajoSwingTipi");
//			System.err.println("aap: "+xe);
//			buildJnlp(sourceFile, destFile);
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}


	protected XMLElement appendExtension(String project) throws IOException {
		try {
			URL rep = new URL(repository);
			URL projectURL = new URL(rep,project+"/");
			URL extensionURL = new URL(projectURL,"definition.xml");

			XMLElement result = getXMLElement(extensionURL);

			parseProjectDefinition(projectURL, result);

			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


protected XMLElement getXMLElement(URL extensionURL)  {
	try {
		XMLElement result = new CaseSensitiveXMLElement();
		InputStream is = extensionURL.openStream();
		Reader r = new InputStreamReader(is);
		result.parseFromReader(r);
		r.close();
		is.close();
		return result;
	} catch (XMLParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
}
	

	public void buildJnlp(File inputPath, File outputPath) throws XMLParseException, IOException {
		XMLElement xe = new CaseSensitiveXMLElement();
		FileReader fr = new FileReader(inputPath);
		xe.parseFromReader(fr);
		XMLElement output = new CaseSensitiveXMLElement("jnlp");
		buildup(xe,output);
		FileWriter fw = new FileWriter(outputPath);
		output.write(fw);
		fw.flush();
		fw.close();
	}



	private void buildup(XMLElement input, XMLElement output) {
		
		XMLElement information =  output.addTagKeyValue("information", "");
		information.addTagKeyValue("vendor",input.getStringAttribute("vendor"));
		information.addTagKeyValue("homepage",input.getStringAttribute("homepage"));
		information.addTagKeyValue("title",input.getStringAttribute("title"));
		
		output.setAttribute("codebase", repository+"/"+input.getStringAttribute("project"));
		output.setAttribute("href", getProject().getProperty("ant.project.name")+".jnlp");
		XMLElement resources = output.addTagKeyValue("resources", "");
		XMLElement description = input.getElementByTagName("description");
		if(description!=null && description.getContent()!=null) {
			input.addTagKeyValue("description", description.getContent().trim());
		}

		XMLElement jars = input.getElementByTagName("jars");
		if(jars!=null) {
			for (XMLElement jar : jars.getChildren()) {
				String path = jar.getStringAttribute("path");
				String main = jar.getStringAttribute("main");
				XMLElement resource = new CaseSensitiveXMLElement("jar");
				resource.setAttribute("href", path);
				resource.setAttribute("main", main==null?"false":true);
				resources.addChild(resource);
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
				xe.setAttribute("href", assemblePath(path,id));
			}
		}
		output.addTagKeyValue("component-desc", "");
		output.setAttribute("spec", "1.0+");
	}


	private String assemblePath(String path, String id) {
		return repository+"/"+path+"/"+id+".jnlp";
	} 
}
