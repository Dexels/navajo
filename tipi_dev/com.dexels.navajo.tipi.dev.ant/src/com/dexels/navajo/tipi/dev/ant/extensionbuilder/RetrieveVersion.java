package com.dexels.navajo.tipi.dev.ant.extensionbuilder;

import java.io.File;
import java.io.FileReader;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;


public class RetrieveVersion extends org.apache.tools.ant.Task {

	private String repository;
	private String destination;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RetrieveVersion.class);
	
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
//		File pomFile = new File(getProject().getBaseDir(),"pom.xml");
//		if(!pomFile.exists()) {
		logger.info("Getting version....");

		logger.info("Loading version in project: "+getProject().getBaseDir().getAbsolutePath() );
				File pomFile = new File(getProject().getBaseDir(),"version.xml");
				if(!pomFile.exists()) {
					getProject().setProperty("tipiComponentVersion", "unknown");
					return;
//				}
		}
		XMLElement pomXml = new CaseSensitiveXMLElement();
		try {
			FileReader reader = new FileReader(pomFile);
			pomXml.parseFromReader(reader);
			reader.close();
		} catch (Exception e) {
			logger.error("Error: ",e);
			logger.info("No version found.");
			getProject().setProperty("tipiComponentVersion", "unknown");
			return;
		}
		XMLElement version = pomXml.getChildByTagName("version");
		logger.info("Version: "+version.getContent());
		getProject().setProperty("tipiComponentVersion", version.getContent());
	}


}
