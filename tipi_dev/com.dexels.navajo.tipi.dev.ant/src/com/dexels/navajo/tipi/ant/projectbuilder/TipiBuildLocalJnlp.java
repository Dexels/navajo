package com.dexels.navajo.tipi.ant.projectbuilder;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;


public class TipiBuildLocalJnlp extends BaseTipiClientTask {
	public void execute() throws BuildException {
		try {
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			List<String> profiles = new ArrayList<String>();
			profiles.add("Local.jnlp");
			l.build(repository,developmentRepository, extensions,getTipiProperties(),getDeployment(), getProject().getBaseDir(),getProject().getBaseDir().toURI().toURL().toString(),profiles,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

}