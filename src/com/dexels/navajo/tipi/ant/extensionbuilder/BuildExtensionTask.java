package com.dexels.navajo.tipi.ant.extensionbuilder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.extensionmanager.ExtensionActions;
import com.dexels.navajo.tipi.util.XMLElement;


public class BuildExtensionTask extends org.apache.tools.ant.Task {

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
		File sourceFile = new File(getProject().getBaseDir(),"src/tipi/"+getProject().getProperty("ant.project.name")+"Extension.xml");
		if(!sourceFile.exists()) {
			throw new BuildException("Tipi project descriptor not found: "+sourceFile.getPath());
		}
		File destDir = new File(getProject().getBaseDir(),destination);
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		try {
			 ExtensionActions.build(repository,getProject().getProperty("ant.project.name"),getProject().getBaseDir(), sourceFile, destDir);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}


}
