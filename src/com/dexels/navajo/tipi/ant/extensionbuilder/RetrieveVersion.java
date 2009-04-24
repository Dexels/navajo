package com.dexels.navajo.tipi.ant.extensionbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.extensionmanager.ExtensionActions;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;
import com.dexels.navajo.tipi.util.XMLParseException;


public class RetrieveVersion extends org.apache.tools.ant.Task {

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


	
	@Override
	public void execute() throws BuildException {
		File pomFile = new File(getProject().getBaseDir(),"pom.xml");
		if(!pomFile.exists()) {
				System.err.println("No version found.");
				getProject().setProperty("tipiComponentVersion", "unknown");
				return;
		}
		XMLElement pomXml = new CaseSensitiveXMLElement();
		try {
			FileReader reader = new FileReader(pomFile);
			pomXml.parseFromReader(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("No version found.");
			getProject().setProperty("tipiComponentVersion", "unknown");
			return;
		}
		XMLElement version = pomXml.getChildByTagName("version");
		System.err.println("Version: "+version.getContent());
		getProject().setProperty("tipiComponentVersion", version.getContent());
	}


}
