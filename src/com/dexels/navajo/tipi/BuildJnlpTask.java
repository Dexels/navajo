package com.dexels.navajo.tipi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.BuildException;


public class BuildJnlpTask extends org.apache.tools.ant.Task {

	private String repository;
	private String destination;

	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getRepository() {
		return repository;
	}


	public void setRepository(String repository) {
		this.repository = repository;
	}


//	public static void main(String[] args) {
//		BuildJnlpTask buildJnlpTask = new BuildJnlpTask();
//		File sourceFile = new File("src/tipi/TipiExtension.xml");
//		File destFile = new File("Aap.xml");
//		try {
//			buildJnlpTask.buildJnlp(sourceFile, destFile);
//		} catch (XMLParseException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//		}

	
	@Override
	public void execute() throws BuildException {
		File sourceFile = new File(getProject().getBaseDir(),"src/tipi/"+getProject().getProperty("ant.project.name")+".xml");
		if(!sourceFile.exists()) {
			throw new BuildException("Tipi project descriptor not found: "+sourceFile.getPath());
		}
		File destDir = new File(getProject().getBaseDir(),destination);
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		try {
			build(sourceFile, destDir);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}


	public void build(File inputPath, File destDir) throws XMLParseException, IOException {
		FileReader fr = new FileReader(inputPath);
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(fr);
		copyFile(inputPath,new File(destDir,"definition.xml"));
		generateJnlp(destDir, xe);
		generateIndex(destDir, xe);
		extractIncludes(inputPath, destDir, xe);
		fr.close();
	}


	private void copyFile(File inputPath, File file) throws IOException {
		FileInputStream fis = new FileInputStream(inputPath);
		FileOutputStream fos = new FileOutputStream(file);
		copyResource(fos, fis);

	}


	private void extractIncludes(File inputPath, File destDir, XMLElement xe) throws IOException {
		XMLElement includes = xe.getElementByTagName("includes");
		if(includes==null) {
			return;
		}
		List<XMLElement> inList = includes.getChildren();
		if(inList==null) {
			return;
		}
		
		for (XMLElement element : inList) {
			String path = element.getStringAttribute("path");
			File src = new File(getProject().getBaseDir(), "src");
			File p = new File(src, path);
			System.err.println("Include: "+p.exists()+" ("+p+")");
			if(!p.exists()) {
				System.err.println("Not found");
				continue;
			}
			File includeDir = new File(destDir,"includes");
			File ccc = new File(includeDir,path);
			String name = ccc.getName();
			ccc.getParentFile().mkdirs();
			copyFile(p, ccc);
		}
	}

	
	private void generateIndex( File destDir, XMLElement xe) throws IOException {
		File destFile = new File(destDir,"index.html");
		List<XMLElement> links = xe.getElementsByTagName("link");
//		<extension id="TipiCore" requiresMain="" project="NavajoTipi" version="0" title="Tipi Core Library" vendor="Dexels" homepage="http://www.dexels.com">
		String id = xe.getStringAttribute("id");
		FileWriter fw = new FileWriter(destFile);
		fw.write("<html><head><title>Tipi build: "+id+"</title></head><body>");
		fw.write("<h4>"+"Vendor: "+xe.getStringAttribute("vendor")+" </h4>");
		fw.write("<h4>"+"Version: "+xe.getStringAttribute("version")+" </h4>");
		fw.write("<h4>"+"Homepage: "+"More info <a href='"+xe.getStringAttribute("homepage")+"'>"+xe.getStringAttribute("homepage")+"</a></h4>");
		fw.write("<h4>"+"Build time: "+new Date()+" </h4>");
		
		fw.write(xe.getStringAttribute("title")+"<br/>");
		for (XMLElement element : links) {
			fw.write("More info <a href='"+element.getStringAttribute("href")+"'>here</a><br/>");
		}
		fw.write("</body></html>");

		fw.flush();
		fw.close();
	}
	
	private void generateJnlp(File destDir, XMLElement xe) throws IOException {
		File destFile = new File(destDir,getProject().getProperty("ant.project.name")+".jnlp");
		XMLElement output = new CaseSensitiveXMLElement("jnlp");
		buildJnlp(xe,output);
		FileWriter fw = new FileWriter(destFile);
		output.write(fw);
		fw.flush();
		fw.close();
	}



	private void buildJnlp(XMLElement input, XMLElement output) {
		System.err.println("Title: "+input.getStringAttribute("title"));
		XMLElement information =  output.addTagKeyValue("information", "");
		information.addTagKeyValue("vendor",input.getStringAttribute("vendor"));
		information.addTagKeyValue("homepage",input.getStringAttribute("homepage"));
		information.addTagKeyValue("title",input.getStringAttribute("title"));
		
		XMLElement security =  output.addTagKeyValue("security", "");
		security.addTagKeyValue("all-permissions", "");
		
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
				resource.setAttribute("href", "lib/"+path);
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

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
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
