package com.dexels.navajo.tipi.dev.ant.projectbuilder;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.LocalJnlpBuilder;


public class TipiBuildLocalJnlp extends BaseTipiClientTask {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBuildLocalJnlp.class);
	public void execute() throws BuildException {
		try {
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			List<String> profiles = new ArrayList<String>();
			profiles.add("Local.jnlp");
			l.build(repository,developmentRepository, extensions,getTipiProperties(),getDeployment(), getProject().getBaseDir(),getProject().getBaseDir().toURI().toURL().toString(),profiles,false);
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}


	

}