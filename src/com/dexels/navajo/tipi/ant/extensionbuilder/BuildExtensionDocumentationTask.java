package com.dexels.navajo.tipi.ant.extensionbuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.extensionmanager.ExtensionActions;
import com.dexels.navajo.tipi.util.XMLElement;


public class BuildExtensionDocumentationTask extends org.apache.tools.ant.Task {

//	private String repository;
	private String destination;
	private String distribution;
	
	public String getDistribution() {
		return distribution;
	}


	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}

//
//	public String getRepository() {
//		return repository;
//	}
//
//
//	public void setRepository(String repository) {
//		this.repository = repository;
//	}


	public static void main(String[] args) throws IOException {

		ExtensionActions.buildDocumentation(new File("."),"dist","Tipi",new File("tipidoc"));
		//		System.err.println(">> "+ss);

	}

	
	@Override
	public void execute() throws BuildException {

		File destDir = new File(getProject().getBaseDir(),destination);
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		try {
			String projectName = getProject().getProperty("ant.project.name");
			ExtensionActions.buildDocumentation(getProject().getBaseDir(),distribution.toLowerCase(),projectName.toLowerCase(),destDir);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new BuildException("Error building documentation ",e1);
		}
	}


}
