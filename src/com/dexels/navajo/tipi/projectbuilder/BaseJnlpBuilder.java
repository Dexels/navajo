package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public abstract class BaseJnlpBuilder {

	
	protected abstract boolean appendResourceForExtension(XMLElement resources, String repository, String ext);
	protected abstract void appendProxyResource(XMLElement resources, String repository, String mainExtension);
		
	public String appendResources(XMLElement resources, String repository, List<String> extensions) {
		String mainExtension = null;
		for (String ext : extensions) {
			boolean isMain = appendResourceForExtension(resources,repository,ext);
			if(isMain) {
				mainExtension = ext;
			}
		}
		return mainExtension;
	}
	

	public abstract String getJnlpName();
	
	public void build(String repository, String extensions,File baseDir, String codebase, String jarName) throws IOException {
		Map<String,String> params = parseParams(baseDir);
		File jnlpFile = new File(jarName);
		XMLElement output = new CaseSensitiveXMLElement();
		output.setName("jnlp");
		output.setAttribute("version", "1");
		output.setAttribute("spec", "1.0+");
		output.setAttribute("codebase", codebase);
		output.setAttribute("href", jarName);
		
		XMLElement information = output.addTagKeyValue("information", "");
		information.addTagKeyValue("title", params.get("title"));
		information.addTagKeyValue("vendor", params.get("vendor"));
		information.addTagKeyValue("homepage", params.get("homepage"));
		information.addTagKeyValue("icon","").setAttribute("href",  params.get("icon"));

		
		System.err.println("Parsing: "+jarName);
		
		if(params.get("splash")!= null) {
			XMLElement splash = information.addTagKeyValue("splash","");
			splash.setAttribute("href", params.get("splash"));
			splash.setAttribute("kind", "splash");
			
		}
//		List<String> missing = ClientActions.checkExtensions(repository,extensions);
//		if(!missing.isEmpty()) {
//			throw new BuildException("Requested extension(s) not available on repository: "+missing);
//		}
		
		StringTokenizer st = new StringTokenizer(extensions,",");
		List<String> exts = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			exts.add(st.nextToken());
		}
		
		XMLElement resources = output.addTagKeyValue("resources", "");
//		  <j2se version="1.6+" max-heap-size="192m"/>
		XMLElement java = resources.addTagKeyValue("j2se","");
		java.setAttribute("version","1.6+");
		// MAX HEAP SIZE?
		//		java.setAttribute("version","1.6+");
		
		appendSecurity(output,params.get("permissions"));
		String mainExtension = appendResources(resources, repository, exts);
		appendProxyResource(resources, repository, mainExtension);
		
		XMLElement app = output.addTagKeyValue("application-desc", "");
		app.setAttribute("main-class", "tipi.MainApplication");
		appendArguments(app,new ArrayList<String>());

		try {
			FileWriter fw1 = new FileWriter(jnlpFile);
			fw1.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			output.write(fw1);
			fw1.flush();
			fw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendArguments(XMLElement app, ArrayList<String> arrayList) {
		for (String elt : arrayList) {
			app.addTagKeyValue("argument", elt);
		}
	}

	private void appendSecurity(XMLElement output, String security) {

		if("all".equals(security)) {
			XMLElement sec = output.addTagKeyValue("security", "");
			sec.addTagKeyValue("all-permissions", "");
		}
	}


	public  Map<String, String> parseParams(File baseDir) throws IOException {
		File path = new File(baseDir,"tipi.properties");
		Map<String,String> params = new HashMap<String, String>();
		FileReader fr = new FileReader(path);
		PropertyResourceBundle p = new PropertyResourceBundle(fr);
		fr.close();
		Enumeration<String> eb = p.getKeys();
		while (eb.hasMoreElements()) {
			String string = (String) eb.nextElement();
			params.put(string, p.getString(string));
		}
		System.err.println("params: "+params);
		return params;
	}
}
