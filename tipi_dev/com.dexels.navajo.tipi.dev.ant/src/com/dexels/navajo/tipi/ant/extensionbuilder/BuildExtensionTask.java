package com.dexels.navajo.tipi.ant.extensionbuilder;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.extensionmanager.ExtensionActions;


public class BuildExtensionTask extends org.apache.tools.ant.Task {

	private String repository;
	private String destination;
	private String version;
	private String svnUrl;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BuildExtensionTask.class);
	
	public String getSvnUrl() {
		return svnUrl;
	}


	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


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
			 ExtensionActions.build(repository,getProject().getProperty("ant.project.name"),version,getProject().getBaseDir(), sourceFile, destDir);
		} catch (Throwable e) {
			logger.error("Error: ",e);
		}
	}


}
