package com.dexels.navajo.tipi.ant.projectbuilder;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.RemoteJnlpBuilder;


public class TipiBuildRemoteJnlp extends BaseTipiClientTask {
	public void execute() throws BuildException {
		try {
			RemoteJnlpBuilder l = new RemoteJnlpBuilder();
			List<String> profiles = new ArrayList<String>();
			
			profiles.add("Remote.jnlp");
			l.build(repository,developmentRepository, extensions,getTipiProperties(),getDeployment(), getProject().getBaseDir(),getProject().getBaseDir().toURI().toURL().toString(),profiles,false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

}