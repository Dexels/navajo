package com.dexels.navajo.tipi.extensionmanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;


import com.dexels.navajo.tipi.projectbuilder.ComponentMerger;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class TipiCreateTipiBeans extends ExtensionClassdefProcessor {

	@Override
	protected void processTipiContext(URL repository, String extension, String version, List<String> extensions,
			Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions, Map<String, XMLElement> allEvents,
			Map<String, XMLElement> allValues, Map<String, XMLElement> allTypes, Map<String, XMLElement> allFunctions) {
		System.err.println("Current output: "+getOutputDir());
		
		URL adapterFile = getClass().getResource("Adapter.template");
		File javaDir = new File(getOutputDir(),"javafiles");
		javaDir.mkdirs();
		System.err.println("Created: "+javaDir.getAbsolutePath());
		for (Entry<String, XMLElement> entry : allComponents.entrySet()) {
			try {
				XMLElement eee = ComponentMerger.getAssembledClassDef(allComponents, entry.getValue(), entry.getValue().getStringAttribute("name"));
//				System.err.println("Assembled classdef: \n"+eee);
				String className = eee.getStringAttribute("class");
				
				if (className == null) {
					// skip, abstract component
					continue;
				}
//				className = className + "Adapter";
				className = eee.getStringAttribute("name");
//				String packageName = eee.getStringAttribute("package");
				String packageName = extension.toLowerCase();

				String nameCap = className.substring(0,1).toUpperCase()+className.substring(1,className.length());
				
				File file = createPackageStructure(javaDir, packageName);
				file.mkdirs();
				File componentFile = new File(file, nameCap + ".java");
				System.err.println("Writing: "+componentFile.getAbsolutePath());
				if(!componentFile.exists()) {
					FileWriter writer = new FileWriter(componentFile);
					createJava(writer, eee, adapterFile,nameCap,packageName);
					
				} else {
					System.err.println("Warning: Duplicate componentclass: "+componentFile.getAbsolutePath());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			createActionClass(extension, javaDir, allActions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
private void createActionClass(String extension, File javaDir, Map<String, XMLElement> allActions) throws IOException {
	File packageDir = new File(javaDir, extension.toLowerCase());
	packageDir.mkdirs();
	File javaFile = new File(packageDir, extension+"Actions.java");
	URL adapterFile = getClass().getResource("Actions.template");
	FileWriter writer = new FileWriter(javaFile);
	
	List<XMLElement> pp = new ArrayList<XMLElement>();
	
	pp.addAll(allActions.values());
	createActions(extension, writer, pp, adapterFile);

		
}

//<tipiaction name="saveValue" class="TipiSaveValue" package="com.dexels.navajo.tipi.components.swingimpl.actions">
//<description>
//	Opens a file save dialog and saves the value.\\
//	Either the value is a binary (which will be saved as such) or the value will be considered a string.
//</description>
//<param name="value" type="object" />
//</tipiaction>

private void createActions(String extension, Writer writer, List<XMLElement> allActions, URL adapterFile) throws IOException {
	ByteArrayOutputStream ba = new ByteArrayOutputStream();
	InputStream is = adapterFile.openStream();
	copyResource(ba, is);
	is.close();
	String ss = new String(ba.toByteArray());
	StringBuffer content = new StringBuffer();
	
	for (XMLElement xmlElement : allActions) {
		// skip actions belonging to another extension
		if(!extension.equals(xmlElement.getStringAttribute("extension"))) {
			continue;
		}
		appendMethodComment(xmlElement, content);
		String name = xmlElement.getStringAttribute("name");
		name = mapMethodName(name);
		content.append("  public void "+name+"("+getMethodParams(xmlElement.getAllElementsByTagName("param"))+") throws TipiException, TipiBreakException {\n");
		List<XMLElement> params = xmlElement.getAllElementsByTagName("param");
		if (params.size()==0) {
			content.append("    java.util.Map<String, Object> parameters = null;\n");
		} else {
			content.append("    java.util.Map<String, Object> parameters = new java.util.HashMap<String,Object>();\n");
			for (XMLElement elt : params) {
				content.append("    parameters.put(\""+elt.getStringAttribute("name")+"\","+mapMethodName(elt.getStringAttribute("name"))+");\n");
			}	
				
		}
		content.append("   performAction(\""+name+"\",parameters);\n");
		
		content.append("  }\n");
	}
	ss = ss.replaceAll("\\|Content\\|", content.toString());
	ss = ss.replaceAll("\\|ClassName\\|", extension+"Actions");
	writer.write(ss);
	writer.flush();
	writer.close();
}

	private void createJava(Writer writer, XMLElement value, URL adapterFile, String className, String packageName) throws IOException {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		InputStream is = adapterFile.openStream();
		copyResource(ba, is);
		is.close();
		String ss = new String(ba.toByteArray());
		ss = ss.replaceAll("\\|ClassName\\|",className);
		ss = ss.replaceAll("\\|Package\\|", packageName);
		
		StringBuffer sb = new StringBuffer();
		createComponent(value, sb);
		ss = ss.replaceAll("\\|Content\\|", sb.toString());
		writer.write(ss);
		writer.flush();
		writer.close();
}

	private void createComponent(XMLElement component, StringBuffer content) {
	// TODO Auto-generated method stub
	if(component.getStringAttribute("name").equals("label")) {
		System.err.println(component.toString());
	}
	XMLElement values = component.getElementByTagName("values");
	if(values!=null) {
		List<XMLElement> xx = values.getAllElementsByTagName("value");
		for (XMLElement value : xx) {
			appendValue(value,content);
		}
	}
	

	XMLElement methods = component.getElementByTagName("methods");
	if(methods!=null) {
		List<XMLElement> xx = methods.getAllElementsByTagName("method");
		for (XMLElement method : xx) {
			appendMethod(method,content);
		}
	}
}

private void appendMethod(XMLElement method, StringBuffer content) {
	String name = mapMethodName(method.getStringAttribute("name"));

	appendMethodComment(method,content);
	content.append("  public void "+name+"("+getMethodParams(method.getAllElementsByTagName("param"))+") {\n");
	//content.append("    myComponent.performMethod(\""+name+"\",invocation,event);\n");
	List<XMLElement> params = method.getAllElementsByTagName("param");
	if (params.size()==0) {
		content.append("    java.util.Map<String, Object> parameters = null;\n");
	} else {
		content.append("    java.util.Map<String, Object> parameters = new java.util.HashMap<String,Object>();\n");
		for (XMLElement xmlElement : params) {
			String paramName = mapMethodName(xmlElement.getStringAttribute("name"));
			
			content.append("    parameters.put(\""+xmlElement.getStringAttribute("name")+"\","+paramName+");\n");
		}	
			
	}
	content.append("   myComponent.performMethod(\""+name+"\",parameters,invocation,event);\n");
	//		public final void performMethod(String methodName, Map<String,String> params,  TipiAction invocation, TipiEvent event) throws TipiBreakException {

	content.append("  }\n");
}

private void appendMethodComment(XMLElement method, StringBuffer content) {
	XMLElement desc = method.getChildByTagName("description");
	// This doesn't take into account that methods without description can still have parameters with desciprtions.
	if(desc==null) {
		return;
	}
	content.append("/**\n *"+desc.getContent()+"\n");
	List<XMLElement> params = method.getAllElementsByTagName("param");
	for (XMLElement xmlElement : params) {
		XMLElement e = xmlElement.getChildByTagName("description");
		if(e!=null) {
			content.append(" * @param "+xmlElement.getStringAttribute("name")+e.getContent()+"\n");
		}
	}
	
	content.append("\n*/\n");
	
}

private String getMethodParams(Vector<XMLElement> vector) {
	StringBuffer b = new StringBuffer();
	int i = 0;
	for (XMLElement xmlElement : vector) {
		String type = mapType( xmlElement.getStringAttribute("type"),true);
		String name = mapMethodName(xmlElement.getStringAttribute("name"));
		b.append(type+" "+name);
		i++;
		if(i<vector.size()) {
			b.append(",");
		}
	}
	return b.toString();
}

private String mapMethodName(String name) {
	name = name.replaceAll("-", "_");
	if(name.equals("switch") || name.equals("class") || name.equals("break")) {
		// etc
		return name+"Tipi";
	}
	return name;
}

private String mapType(String type, boolean useObjectForCast) {
	if(type.equals("integer")) {
		return useObjectForCast?"Integer":"int";
	}
	if(type.equals("string")) {
		return "String";
	}
	if(type.equals("float")) {
		return useObjectForCast?"Double":"double";
	}
	if(type.equals("boolean")) {
		return useObjectForCast?"Boolean":"boolean";
	}

	if(type.equals("navajo")) {
		return "com.dexels.navajo.document.Navajo";
	}
	if(type.equals("message")) {
		return "com.dexels.navajo.document.Message";
	}
	if(type.equals("property")) {
		return "com.dexels.navajo.document.Property";
	}
	if(type.equals("selection")) {
		return "com.dexels.navajo.document.Selection";
	}
	if(type.equals("component")) {
		return "com.dexels.navajo.tipi.TipiComponent";
	}
	if(type.equals("url") || type.equals("resource")) {
		return "java.net.URL";
	}

	return "Object";

}

private void appendValue(XMLElement value, StringBuffer content) {
	String dir = value.getStringAttribute("direction");
	if(dir==null) {
		return;
	}
	String name = mapMethodName(value.getStringAttribute("name"));
	String type = mapType(value.getStringAttribute("type"),true);
	String castType = mapType(value.getStringAttribute("type"),true);
	String nameCap = name.substring(0,1).toUpperCase()+name.substring(1,name.length());
//	System.err.println("Appending value: "+dir+" name: "+name+" type: "+type+" castType: "+castType);
	if(dir.indexOf("in")!=-1) {
		// create setter
		content.append("  public void set"+nameCap+"("+type+" value) {\n");
		content.append("    myComponent.setValue(\""+name+"\",value);\n");
		content.append("  }\n");
	}
	if(dir.indexOf("out")!=-1) {
		content.append("  public "+type+" get"+nameCap+"() {\n");
		content.append("    return ("+castType+")myComponent.getValue(\""+name+"\");\n");
		content.append("  }\n");
	}
}

public static void main(String[] args) throws IOException {

	String aa = "aap-noot";
	System.err.println(aa.replaceAll("-", "_"));
	if(true) {
		return;
	}
	URL adapterFile = TipiCreateTipiBeans.class.getResource("Adapter.template");
	URL actionsFile = TipiCreateTipiBeans.class.getResource("Actions.template");
	URL template = TipiCreateTipiBeans.class.getResource("examplecomponent.xml");
	InputStreamReader isr = new InputStreamReader(template.openStream());
	XMLElement xe = new CaseSensitiveXMLElement();
	xe.parseFromReader(isr);

	TipiCreateTipiBeans tcci = new TipiCreateTipiBeans();
	StringWriter sw = new StringWriter();
	List<XMLElement> ll =  xe.getAllElementsByTagName("tipiclass");
//	for (XMLElement xmlElement : ll) {
//		tcci.createJava(sw, xmlElement, adapterFile);
//		
//	}

	ll =  xe.getAllElementsByTagName("tipiaction");
	tcci.createActions("Tipi",sw, ll, actionsFile);

	for (XMLElement xmlElement : ll) {
		
	}

	System.err.println("Res:\n"+sw.toString());
}
//	public void execute(URL repository, String originalExtension, String version, Map<String,List<XMLElement>> classDefElements, String repositoryDeploy) {
//public static Map<String,List<XMLElement>> getAllClassDefs(String currentProject, String remoteRepository, URL repository, List<String> projects) throws IOException {
		

	private File createPackageStructure(File currentDir, String packagePath) {
		if(packagePath.indexOf(".")==-1) {
			File last = new File(currentDir,packagePath);
			last.mkdirs();
			return last;
		} else {
			int index = packagePath.indexOf('.');
			String firstPart = packagePath.substring(0,index);
			File current = new File(currentDir,firstPart);
			current.mkdirs();
			return createPackageStructure(current, packagePath.substring(index+1,packagePath.length()));
		}
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
}
