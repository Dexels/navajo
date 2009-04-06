package com.dexels.navajo.tipi.extensionmanager;

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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class ExtensionActions {

	public static void build(String repository, String projectName,File baseDir, File inputPath, File destDir) throws XMLParseException, IOException {
		System.err.println("Building for repository: "+repository);
		XMLElement xe = parseXmlFile(inputPath);
		
		String version = xe.getStringAttribute("version");
		ExtensionManager.registerExtension(projectName, repository, destDir,version);

		copyFile(inputPath,new File(destDir,"definition.xml"));
		
		
		generateJnlp(repository,projectName, destDir, xe);
		generateIndex(destDir, xe);
		List<XMLElement> locatedIncludes = new ArrayList<XMLElement>();
		extractIncludes(baseDir, inputPath, destDir, xe,locatedIncludes);
		Map<String,List<XMLElement>> tipiParts = new HashMap<String, List<XMLElement>>();
		for (XMLElement element : locatedIncludes) {
			appendClassDefElement(projectName, element, tipiParts);
		}
		
		
	}
	
	public static Map<String,List<XMLElement>> getAllClassDefs(URL repository, List<String> projects) throws IOException {
		List<String> toBeresolved = new LinkedList<String>();
		List<String> resolved = new LinkedList<String>();
		toBeresolved.addAll(projects);
		Map<String,List<XMLElement>> result = new HashMap<String,List<XMLElement>>();
		extractRemoteClassDefs(repository,resolved, toBeresolved,result);
		return result;
	}


	public static void main(String[] args) throws IOException {

//		buildDocumentation(new File("../NavajoTipi/dist"),"Tipi",new File("tipidoc"));
		//		System.err.println(">> "+ss);

	}

	public static void buildDocumentation(URL repository, String project, File destDir) throws IOException {
		
		
		project = project.toLowerCase();
		
		
		
//
	buildSingleDocumentation ( repository, project,destDir);
	}
	  
	public static void buildDocumentation(String repository, String projectName,File baseDir, File inputPath, File destDir) throws XMLParseException, IOException {
		
	}
	
	public static void buildDocumentation(File baseDir, String sourcePath, String project, File destDir) throws IOException {
		File sourceDir = new File(baseDir,sourcePath);
		URL u = sourceDir.toURI().toURL();
		System.err.println("Building documentation::: "+sourceDir+" to: "+destDir);
		buildDocumentation(u, project, destDir);
	}	
	
	public static void buildSingleDocumentation(URL repository, String project, File destDir) throws IOException {
		List<String> projects = new ArrayList<String>();
		projects.add(project);
		Map<String,List<XMLElement>> ss = getAllClassDefs(repository, projects);
		TipiCreateWikiDocumentation ecdp = new TipiCreateWikiDocumentation();
		ecdp.setOutputDir(destDir);
		ecdp.execute(project,ss);
		System.err.println("Elements found: "+ss.size());
	}
	
	private static void extractRemoteClassDefs(URL repository, List<String> resolved, List<String> toBeResolved, Map<String,List<XMLElement>> result) throws IOException {
		if(toBeResolved.isEmpty()) {
			return;
		}
		String project = toBeResolved.get(0);
		resolved.add(project);
		toBeResolved.remove(0);
		XMLElement projectDefinition = downloadDefinition(repository, project);
		XMLElement requires = projectDefinition.getElementByTagName("requires");
		if(requires!=null) {
			List<XMLElement> children = requires.getChildren();
			for (XMLElement element : children) {
				String req = element.getStringAttribute("path");
				// If it hasn't been processed yet, and it also isnt already in the queue
				if(!resolved.contains(req) && !toBeResolved.contains(req)) {
					toBeResolved.add(req);
				}
			}
		}
		
		XMLElement includes = projectDefinition.getElementByTagName("includes");
		if(includes!=null) {
			List<XMLElement> children = includes.getChildren();
			for (XMLElement element : children) {
				String path = element.getStringAttribute("path");
				XMLElement xe = downloadInclude(repository, project, path);
				List<XMLElement> res = result.get(project);
				if(res==null) {
					res = new ArrayList<XMLElement>();
					result.put(project, res);
				}
				res.add(xe);
			}
		}
		extractRemoteClassDefs(repository, resolved, toBeResolved, result);
	}

	private static XMLElement downloadInclude(URL repository, String project, String path) throws IOException {
		URL def = new URL(repository,"includes/"+path);
		return parseXmlFile(def);
	}
	
	private static XMLElement downloadDefinition(URL repository, String project) throws IOException {
	//	URL base = new URL(repository);
//		URL def = new URL(repository,project+"/definition.xml");
		URL def = new URL(repository,"definition.xml");
		return parseXmlFile(def);
	}

	private static XMLElement parseXmlFile(File inputPath) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(inputPath);
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(fr);
		fr.close();
		return xe;
	}

	private static XMLElement parseXmlFile(URL inputURL) throws FileNotFoundException, IOException {
		Reader fr = new InputStreamReader(inputURL.openStream());
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(fr);
		fr.close();
		return xe;
	}


	private static void copyFile(File inputPath, File file) throws IOException {
		FileInputStream fis = new FileInputStream(inputPath);
		FileOutputStream fos = new FileOutputStream(file);
		copyResource(fos, fis);

	}
	
	private static void appendClassDefElement(String extension, XMLElement xx, Map<String,List<XMLElement>> tipiParts) {
		List<XMLElement> cc = xx.getChildren();
		for (XMLElement element : cc) {
			List<XMLElement> elts = tipiParts.get(element.getName());
			element.setAttribute("extension", extension);
			if(elts==null) {
				elts = new LinkedList<XMLElement>();
				tipiParts.put(element.getName(), elts);
			}
			elts.add(element);
		}

	}

//
//	private static List<XMLElement> extractRecursiveTidList(File baseDir, File inputPath, File destDir, XMLElement definitions, List<XMLElement> locatedIncludes, List<String> toBeresolved) throws IOException {
//
//		XMLElement includes = definitions.getElementByTagName("includes");
//		List<XMLElement> inList = includes.getChildren();
//		if(inList==null) {
//			return;
//		}
//		
//		for (XMLElement element : inList) {
//			String path = element.getStringAttribute("path");
//			File src = new File(baseDir, "src");
//			File p = new File(src, path);
//			XMLElement foundFile = new CaseSensitiveXMLElement();
//			FileReader fw = new FileReader(p);
//			foundFile.parseFromReader(fw);
//			fw.close();
//			locatedIncludes.add(foundFile);
//			System.err.println("Include: "+p.exists()+" ("+p+")");
//			if(!p.exists()) {
//				System.err.println("Not found");
//				continue;
//			}
//			File includeDir = new File(destDir,"includes");
//			File ccc = new File(includeDir,path);
//			ccc.getParentFile().mkdirs();
//			copyFile(p, ccc);
//		}
//	}
//	
	private static void extractIncludes(File baseDir, File inputPath, File destDir, XMLElement definitions, List<XMLElement> locatedIncludes) throws IOException {
		XMLElement includes = definitions.getElementByTagName("includes");
		if(includes==null) {
			return;
		}
		List<XMLElement> inList = includes.getChildren();
		if(inList==null) {
			return;
		}
		
		for (XMLElement element : inList) {
			String path = element.getStringAttribute("path");
			File src = new File(baseDir, "src");
			File p = new File(src, path);
			XMLElement foundFile = new CaseSensitiveXMLElement();
			FileReader fw = new FileReader(p);
			foundFile.parseFromReader(fw);
			fw.close();
			locatedIncludes.add(foundFile);
			System.err.println("Include: "+p.exists()+" ("+p+")");
			if(!p.exists()) {
				System.err.println("Not found");
				continue;
			}
			File includeDir = new File(destDir,"includes");
			File ccc = new File(includeDir,path);
			ccc.getParentFile().mkdirs();
			copyFile(p, ccc);
		}
	}

	
	private static void generateIndex( File destDir, XMLElement xe) throws IOException {
		File destFile = new File(destDir,"index.html");
		List<XMLElement> links = xe.getElementsByTagName("link");
//		<extension id="TipiCore" requiresMain="" project="NavajoTipi" version="0" title="Tipi Core Library" vendor="Dexels" homepage="http://www.dexels.com">
		String id = xe.getStringAttribute("id");
		FileWriter fw = new FileWriter(destFile);
		fw.write("<html><head><title>Tipi build: "+id+"</title></head><body>\n");
		fw.write("<h4>"+"Vendor: "+xe.getStringAttribute("vendor")+" </h4>\n");
		fw.write("<h4>"+"Version: "+xe.getStringAttribute("version")+" </h4>\n");
		fw.write("<h4>"+"Homepage: "+"More info <a href='"+xe.getStringAttribute("homepage")+"'>"+xe.getStringAttribute("homepage")+"</a></h4>");
		fw.write("<h4>"+"Build time: "+new Date()+" </h4>\n");
		
		fw.write(xe.getStringAttribute("title")+"<br/>");
		for (XMLElement element : links) {
			fw.write("More info <a href='"+element.getStringAttribute("href")+"'>here</a><br/>");
		}
		
		String webstart = xe.getStringAttribute("project")+".jnlp";
		writeLink("Webstart: "+webstart+"<br/>",webstart,fw);
		writeLink("Definition: definition.xml<br/>","definition.xml",fw);
		fw.write("</body></html>");

		fw.flush();
		fw.close();
	}
	
	private static void writeLink(String label, String href,Writer w) throws IOException {
		w.write("<a href='"+href+"'>"+label+"</a>\n");
	}


	private static void generateJnlp(String repository,String projectName, File destDir, XMLElement xe) throws IOException {
		File destFile = new File(destDir,projectName+".jnlp");
		XMLElement output = new CaseSensitiveXMLElement("jnlp");
		buildJnlp(repository,projectName, xe,output);
		FileWriter fw = new FileWriter(destFile);
		output.write(fw);
		fw.flush();
		fw.close();
	}



	private static void buildJnlp(String repository, String projectName, XMLElement input, XMLElement output) {
		System.err.println("Title: "+input.getStringAttribute("title"));
		XMLElement information =  output.addTagKeyValue("information", "");
		information.addTagKeyValue("vendor",input.getStringAttribute("vendor"));
		information.addTagKeyValue("homepage",input.getStringAttribute("homepage"));
		information.addTagKeyValue("title",input.getStringAttribute("title"));
		
		XMLElement security =  output.addTagKeyValue("security", "");
		security.addTagKeyValue("all-permissions", "");
		
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
				if(jar.getName().equals("jar")) {
					String path = jar.getStringAttribute("path");
					String main = jar.getStringAttribute("main");
					XMLElement resource = new CaseSensitiveXMLElement("jar");
					resource.setAttribute("href", "lib/"+path);
					resource.setAttribute("main", main==null?"false":true);
					resources.addChild(resource);
				}
				if(jar.getName().equals("remotejnlp")) {
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

	private static final void copyResource(OutputStream out, InputStream in) throws IOException {
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
//	 ExtensionActions.build(repository,getProject().getProperty("ant.project.name"),getProject().getBaseDir(), sourceFile, destDir);


}
