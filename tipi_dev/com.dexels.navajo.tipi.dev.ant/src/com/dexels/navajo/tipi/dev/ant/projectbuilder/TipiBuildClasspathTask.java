package com.dexels.navajo.tipi.dev.ant.projectbuilder;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.ClasspathBuilder;


public class TipiBuildClasspathTask extends BaseTipiClientTask {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBuildClasspathTask.class);
	@Override
	public void execute() throws BuildException {
		try {
			ClasspathBuilder cb = new ClasspathBuilder();
			cb.build(repository, extensions,getProject().getBaseDir());
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}


	

}