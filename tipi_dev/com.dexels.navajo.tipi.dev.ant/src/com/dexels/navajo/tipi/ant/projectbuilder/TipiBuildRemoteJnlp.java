package com.dexels.navajo.tipi.ant.projectbuilder;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.projectbuilder.RemoteJnlpBuilder;


public class TipiBuildRemoteJnlp extends BaseTipiClientTask {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBuildRemoteJnlp.class);
	public void execute() throws BuildException {
		try {
			RemoteJnlpBuilder l = new RemoteJnlpBuilder();
			List<String> profiles = new ArrayList<String>();
			
			profiles.add("Remote.jnlp");
			l.build(repository,developmentRepository, extensions,getTipiProperties(),getDeployment(), getProject().getBaseDir(),getProject().getBaseDir().toURI().toURL().toString(),profiles,false);

		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}


	

}