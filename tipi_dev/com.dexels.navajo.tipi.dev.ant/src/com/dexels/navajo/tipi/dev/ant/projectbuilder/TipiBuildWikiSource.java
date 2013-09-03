package com.dexels.navajo.tipi.dev.ant.projectbuilder;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.TipiSourceDeployer;


public class TipiBuildWikiSource  extends org.apache.tools.ant.Task {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBuildWikiSource.class);
	
	private String docDir;
	private String sourceDir;
	private String resourceDir;
	private String appCodebase;
	
	public String getAppCodebase() {
		return appCodebase;
	}

	public void setAppCodebase(String appCodebase) {
		this.appCodebase = appCodebase;
	}

	public String getResourceDir() {
		return resourceDir;
	}

	public void setResourceDir(String resourceDir) {
		this.resourceDir = resourceDir;
	}

	private String baseDir;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getDocDir() {
		return docDir;
	}

	public void setDocDir(String docDir) {
		this.docDir = docDir;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void execute() throws BuildException {
		try {
			TipiSourceDeployer jd = new TipiSourceDeployer();
			
			File currentBaseDir = new File(getBaseDir());
			File sourceDir = new File(currentBaseDir,getSourceDir());
			File currentResourceDir = new File(currentBaseDir,getResourceDir());
			File docDir = new File(currentBaseDir,getDocDir());
			logger.info("Source dir: "+sourceDir.getAbsolutePath());
			logger.info("Docdir: "+docDir.getAbsolutePath());
			if(!docDir.exists()) {
				docDir.mkdirs();
			}
			jd.deploy(name.toLowerCase(),sourceDir,docDir,currentResourceDir,appCodebase);
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
	}


	

}