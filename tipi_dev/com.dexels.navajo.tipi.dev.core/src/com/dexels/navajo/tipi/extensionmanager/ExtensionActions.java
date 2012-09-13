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
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.dexels.navajo.tipi.projectbuilder.VersionResolver;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;

public class ExtensionActions {

	public static void build(String baseRepository, String projectName,String version, File baseDir, File inputPath, File destDir) throws XMLParseException, IOException {
		XMLElement xe = parseXmlFile(inputPath);
		String extensionRepository = getExtensionRepository(baseRepository);
//		String version = xe.getStringAttribute("version");
		ExtensionManager.registerExtension(projectName, extensionRepository, destDir,version);

		copyFile(inputPath,new File(destDir,"definition.xml"));
		
		
		generateJnlp(extensionRepository,projectName,version, destDir, xe);
		generateIndex(destDir, xe);
		List<XMLElement> locatedIncludes = new ArrayList<XMLElement>();
		extractIncludes(baseDir, destDir, xe,locatedIncludes);
		Map<String,List<XMLElement>> tipiParts = new HashMap<String, List<XMLElement>>();
		for (XMLElement element : locatedIncludes) {
			appendClassDefElement(projectName, element, tipiParts);
		}
		
		mergeTypeMap(new URL(extensionRepository), destDir, tipiParts);
		
	}
	
	public static String getExtensionRepository(String repository) {
		return repository+"Extensions/";
	}
	
	private static void mergeTypeMap(URL repository, File destDir, Map<String, List<XMLElement>> tipiParts) throws IOException {
		List<XMLElement> parsers = tipiParts.get("tipi-parser");
	//	System.err.println("Parserl: "+parsers.size());
		Map<String,String> parserMap = new TreeMap<String, String>();
		try {
			parseParserMap(new URL(repository,"typemap.xml"), parserMap);
		} catch (Exception e) {
			System.err.println("Parse failed. Never mind.");
		}
		if(parsers!=null) {
			for (XMLElement element : parsers) {
				parserMap.put(element.getStringAttribute("name"), element.getStringAttribute("extension"));
			}
		
		}
		writeParserMap(destDir, parserMap);
	}

	protected static void parseParserMap(URL parserLink, Map<String,String> parserMap) throws IOException {
		InputStreamReader isr = new InputStreamReader(parserLink.openStream());
		XMLElement pp = new CaseSensitiveXMLElement();
		pp.parseFromReader(isr);
		isr.close();
		List<XMLElement> cc = pp.getChildren();
		for (XMLElement element : cc) {
			parserMap.put(element.getStringAttribute("type"), element.getStringAttribute("extension"));
		}
	}	
	
	private static void writeParserMap(File destDir, Map<String,String> parsers) throws IOException {
		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("typemap");
		for (Entry<String, String> element : parsers.entrySet()) {
			XMLElement r = new CaseSensitiveXMLElement("type");
			r.setAttribute("type", element.getKey());
			r.setAttribute("extension", element.getValue());
			result.addChild(r);
		}

		FileWriter fw = new FileWriter(new File(destDir,"typemap.xml"));
		result.write(fw);
		fw.flush();
		fw.close();
	}

/**
 * Current project is needed, because only the current project is resolved locally
 * the rest is resolved from the remote repository	
 * @param currentProject
 * @param remoteRepository
 * @param repository
 * @param projects
 * @return
 * @throws IOException
 */

	public static Map<String,List<XMLElement>> getAllClassDefs(String currentProject, String remoteRepository, URL repository, List<String> projects) throws IOException {
		System.err.println("Getting classdefs from rep: "+repository+" projects: "+projects);
		List<String> toBeresolved = new LinkedList<String>();
		List<String> resolved = new LinkedList<String>();
		toBeresolved.addAll(projects);
		Map<String,List<XMLElement>> result = new HashMap<String,List<XMLElement>>();
		extractRemoteClassDefs(currentProject, remoteRepository,repository,resolved, toBeresolved,result);
		return result;
	}


	public static void main(String[] args) {

//		buildDocumentation(new File("../NavajoTipi/dist"),"Tipi",new File("tipidoc"));
		//		System.err.println(">> "+ss);

	}

	public static void buildDocumentation(URL repository,File distributionPath, String project, String version, File destDir, String repositoryDeploy) throws IOException {
	//	project = project.toLowerCase();
		buildSingleDocumentation ( repository,distributionPath, project,version, destDir, repositoryDeploy);
	}

	
	public static void buildDocumentation(File baseDir, String distributionPath,String sourcePath, String project, String version, File destDir,String repositoryDeploy) throws IOException {
		File sourceDir = new File(baseDir,sourcePath);
		URL u = sourceDir.toURI().toURL();
//		System.err.println("Building documentation. Url: "+u.toString()+"from source dir: "+sourceDir);
		if(!u.toString().endsWith("/")) {
			System.err.println("Strange. No slash. Replacing");
			u = new URL(u.toString()+"/");
			System.err.println("New version: "+u.toString());
		} 
		buildDocumentation(u, new File(baseDir,distributionPath), project,version, destDir,repositoryDeploy);
	}	
	
	
	public static void buildSingleDocumentation(URL repository,File distributionPath, String project, String version, File destDir,String repositoryDeploy) throws IOException {
		List<String> projects = new ArrayList<String>();
		projects.add(project);
		Map<String,List<XMLElement>> ss = getAllClassDefs(project, repositoryDeploy, repository, projects);
		TipiCreateWikiDocumentation ecdp = new TipiCreateWikiDocumentation();
		ecdp.setOutputDir(destDir);
		ecdp.setDistributionDir(distributionPath);
		ecdp.setDeployRepository(repositoryDeploy);
		ecdp.execute(repository,project,version,ss);
	}
	public static void buildTipiBeans(File baseDir, String distributionPath,String sourcePath, String project, String version, File destDir,String repositoryDeploy) throws IOException {
		File sourceDir = new File(baseDir,sourcePath);
		URL u = sourceDir.toURI().toURL();
		System.err.println("Building documentation. Url: "+u.toString()+"from source dir: "+sourceDir);
		if(!u.toString().endsWith("/")) {
			System.err.println("Strange. No slash. Replacing");
			u = new URL(u.toString()+"/");
			System.err.println("New version: "+u.toString());
		} 
		buildTipiBeans(u, new File(baseDir,distributionPath), project,version, destDir,repositoryDeploy);
	}	
	private static void buildTipiBeans(URL repository,File distributionPath, String project, String version, File destDir,String repositoryDeploy) throws IOException {
		List<String> projects = new ArrayList<String>();
		projects.add(project);
		Map<String,List<XMLElement>> ss = getAllClassDefs(project, repositoryDeploy, repository, projects);
		
		TipiCreateTipiBeans tcci = new TipiCreateTipiBeans();
		tcci.setOutputDir(destDir);
		tcci.setDistributionDir(distributionPath);
		tcci.setDeployRepository(repositoryDeploy);
		tcci.execute(repository,project,version,ss);
		
	}
	
	private static void extractRemoteClassDefs(String currentProject, String remoteRepository,URL repository, List<String> resolved, List<String> toBeResolved, Map<String,List<XMLElement>> result) throws IOException {
		System.err.println("Resolved: "+resolved);
		System.err.println("to be resolved: "+toBeResolved);
		System.err.println("Current project: "+currentProject+" remoteRep:  "+remoteRepository);
		if(toBeResolved.isEmpty()) {
			return;
		}
		String project = toBeResolved.get(0);
		resolved.add(project);
		toBeResolved.remove(0);
		
		XMLElement projectDefinition = downloadDefinition(currentProject,remoteRepository,  repository, project);
		System.err.println("Downloading (EA): "+project);
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
				XMLElement xe = downloadInclude(currentProject,remoteRepository, repository, project, path);
				System.err.println("Downloadinginclude: "+path);
				List<XMLElement> res = result.get(project);
				if(res==null) {
					res = new ArrayList<XMLElement>();
					result.put(project, res);
				}
				res.add(xe);
			}
		}
		extractRemoteClassDefs(currentProject, remoteRepository, repository, resolved, toBeResolved, result);
	}

	private static XMLElement downloadInclude(String currentProject, String remoteRepository, URL repository, String project, String path) throws IOException {
			if (currentProject.equals(project)) {
				URL def = new URL(repository,"includes/"+path);
				return parseXmlFile(def);
				

		} else {
				URL base = new URL(remoteRepository);
			VersionResolver vr = new VersionResolver(remoteRepository);
			String pathtt =  vr.resultVersionPath(project);
			System.err.println("PathResult: "+pathtt);
		   URL pathUrl = new URL(base,pathtt+"/includes"+"/"+path);
		   System.err.println("Pathurl: "+pathUrl);
//			URL def = new URL(pathUrl,path);
		  // System.err.println("def: "+def);
		   
			return parseXmlFile(pathUrl);

		}
	}
	
	private static XMLElement downloadDefinition(String currentProject, String remoteRepository, URL localRepository, String project) throws IOException {
		if (currentProject.equals(project)) {
			System.err.println("Local rep: "+localRepository);
			URL def = new URL(localRepository,"definition.xml");
			System.err.println("Resuult: "+def);
			return parseXmlFile(def);

		} else {
				URL base = new URL(remoteRepository);
			VersionResolver vr = new VersionResolver(remoteRepository);
			String path =  vr.resultVersionPath(project);
			System.err.println("PathResult: "+path);
		   URL pathUrl = new URL(base,path+"/");
		   System.err.println("Pathurl: "+pathUrl);
			URL def = new URL(pathUrl,"definition.xml");
		   System.err.println("def: "+def);
		   
			return parseXmlFile(def);

		}
	}

	private static XMLElement parseXmlFile(File inputPath) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(inputPath);
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(fr);
		fr.close();
		return xe;
	}

	private static XMLElement parseXmlFile(URL inputURL) throws FileNotFoundException, IOException {
		InputStream openStream = inputURL.openStream();
		Reader fr = new InputStreamReader(openStream);
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(fr);
		openStream.close();
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
	private static void extractIncludes(File baseDir,  File destDir, XMLElement definitions, List<XMLElement> locatedIncludes) throws IOException {
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
			fw.write("More info <a href='"+element.getStringAttribute("href")+"'>here</a><br/>\n");
		}
		
		String webstart = xe.getStringAttribute("project")+".jnlp";
		writeLink("Webstart: "+webstart+"<br/>\n",webstart,fw);
		writeLink("Definition: definition.xml<br/>\n","definition.xml",fw);
		fw.write("</body></html>");

		fw.flush();
		fw.close();
	}
	
	private static void writeLink(String label, String href,Writer w) throws IOException {
		w.write("<a href='"+href+"'>"+label+"</a>\n");
	}


	private static void generateJnlp(String repository,String projectName, String version, File destDir, XMLElement xe) throws IOException {
		File destFile = new File(destDir,projectName+".jnlp");
		XMLElement output = new CaseSensitiveXMLElement("jnlp");
		buildJnlp(repository,projectName, version,xe,output);
		FileWriter fw = new FileWriter(destFile);
		output.write(fw);
		fw.flush();
		fw.close();
	}



	private static void buildJnlp(String repository, String projectName, String version, XMLElement input, XMLElement output) throws IOException {
		VersionResolver vr = new VersionResolver(repository);
		System.err.println("Title: "+input.getStringAttribute("title"));
		XMLElement information =  output.addTagKeyValue("information", "");
		information.addTagKeyValue("vendor",input.getStringAttribute("vendor"));
		information.addTagKeyValue("homepage",input.getStringAttribute("homepage"));
		information.addTagKeyValue("title",input.getStringAttribute("title"));
		
		XMLElement security =  output.addTagKeyValue("security", "");
		security.addTagKeyValue("all-permissions", "");
		
		output.setAttribute("codebase", repository+input.getStringAttribute("project")+"/"+version);
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
				if(path==null) {
					throw new IllegalArgumentException("Path attribute missing from 'require' tag in project: "+projectName);
				}
				xe.setAttribute("href", assemblePath(repository, vr.resultVersionPath(path),id));
			}
		} 
		output.addTagKeyValue("component-desc", "");
		output.setAttribute("spec", "1.0+");
	}


	private static String assemblePath(String repository, String path, String id) {
		return repository+path+"/"+id+".jnlp";
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
