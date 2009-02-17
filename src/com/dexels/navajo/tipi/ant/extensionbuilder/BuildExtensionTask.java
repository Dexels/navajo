package com.dexels.navajo.tipi.ant.extensionbuilder;

import java.io.File;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.extensionmanager.ExtensionActions;


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


//	public static void main(String[] args) {
//		BuildJnlpTask buildJnlpTask = new BuildJnlpTask();
//		File sourceFile = new File("src/tipi/TipiExtension.xml");
//		File destFile = new File("Aap.xml");
//		try {
//			buildJnlpTask.buildJnlp(sourceFile, destFile);
//		} catch (XMLParseException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//		}

	
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
//			public static void build(String repository, String projectName,File baseDir, File inputPath, File destDir) throws XMLParseException, IOException {
				
			 ExtensionActions.build(repository,getProject().getProperty("ant.project.name"),getProject().getBaseDir(), sourceFile, destDir);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}


}
