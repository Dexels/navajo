package com.dexels.navajo.tipi.ant.extensionbuilder;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.extensionmanager.ExtensionActions;


public class BuildExtensionDocumentationTask extends org.apache.tools.ant.Task {

	private String repository;
	private String destination;
	private String distribution;
	private String version;
	
	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


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
	public String getRepository() {
		return repository;
	}


	public void setRepository(String repository) {
		this.repository = repository;
	}


	public static void main(String[] args) throws IOException {
		ExtensionActions.buildDocumentation(new File("../NavajoTipi"),"dist","dist","NavajoTipi","1.0.20",new File("../NavajoTipi/aap"),"http://spriritus.dexels.nl:41766/Tipi/Extensions");
	}

	
	@Override
	public void execute() throws BuildException {

		File destDir = new File(getProject().getBaseDir(),destination);
		if(!destDir.exists()) {
			destDir.mkdirs();
		}
		try {
			String projectName = getProject().getProperty("ant.project.name");
			ExtensionActions.buildDocumentation(getProject().getBaseDir(),distribution,distribution,projectName,version,destDir,repository);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new BuildException("Error building documentation ",e1);
		}
	}


}
