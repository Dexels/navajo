package com.dexels.navajo.dev.ant.feature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;


public class ExtractFeatures extends Task {

	private static final String DEFAULT_NAMESPACE = "http://karaf.apache.org/xmlns/features/v1.2.0";
	private String destination;
	private String input;
	private String namespace;
	private boolean clean = false;


	public ExtractFeatures() {
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public void setClean(boolean clean) {
		this.clean = clean;
	}

	@Override
	public void execute() throws BuildException {
		File inputFile = new File(input);
		File sourceFile;
		if(!inputFile.isAbsolute()) {
			sourceFile = new File(getProject().getBaseDir(),input);
		} else {
			sourceFile = inputFile;
		}
		if(!sourceFile.exists()) {
			throw new BuildException("Tipi project descriptor not found: "+sourceFile.getPath());
		}
		File outputFile = new File(destination);
		File destDir;
		if(!outputFile.isAbsolute()) {
			destDir = new File(getProject().getBaseDir(),destination);
		} else {
			destDir = outputFile;
		}
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		build(sourceFile,destDir);
	}

	private void build(File sourceFile, File destDir) {
		try {
			XMLElement xe = parseXmlFile(sourceFile);
			if(!xe.getName().equals("features")) {
				throw new IllegalArgumentException("Top level name should be 'features' not: "+xe.getName());
			}
			System.err.println("Writing features to destination: "+destDir.getAbsolutePath());
			List<XMLElement> l = xe.getChildren();
			for (XMLElement xmlElement : l) {
				convertElement(xmlElement,destDir);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void convertElement(XMLElement xmlElement, File destDir) throws IOException {
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		String featureName = xmlElement.getStringAttribute("name")+"-"+xmlElement.getStringAttribute("version");
//		<features xmlns="http://karaf.apache.org/xmlns/features/v1.0.0" name="navajo.repo-[[VERSION]]">
		XMLElement features = new CaseSensitiveXMLElement("features");
		features.setAttribute("xmlns", this.namespace==null?DEFAULT_NAMESPACE:namespace);
		features.setAttribute("name", featureName);
//		features.setAttribute("bla", "ble");
		
		if(clean) {
			System.err.println("Clean ON!");
			xmlElement.removeAttribute("resolver");
			for(XMLElement bundle : xmlElement.getAllElementsByTagName("bundle")) {
				bundle.removeAttribute("dependency");
				System.err.println("Cleaning: "+bundle);
			}
			for(XMLElement feature : xmlElement.getAllElementsByTagName("feature")) {
				feature.removeAttribute("resolver");
				System.err.println("Cleaning feature: "+feature);
			}

		}
		features.addChild(xmlElement);
		String outputName = featureName+".xml";
		File outputFile = new File(destDir,outputName);
		System.err.println("Writing feature: "+outputFile);
		FileWriter fw = new FileWriter(outputFile);
		fw.write("<?xml version='1.0' encoding='utf-8'?>\n");
		features.write(fw);
		fw.close();
	}
		
	private static XMLElement parseXmlFile(File inputPath) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(inputPath);
		XMLElement xe = new CaseSensitiveXMLElement();
		try {
			xe.parseFromReader(fr);
		} catch (XMLParseException e) {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e1) {
				}
			}
			throw e;
		} catch (IOException e) {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e1) {
				}
			}
			throw e;		}
		try {
			fr.close();
		} catch (IOException e) {
		}
		return xe;
	}

}
