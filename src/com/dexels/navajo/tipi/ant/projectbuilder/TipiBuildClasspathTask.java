package com.dexels.navajo.tipi.ant.projectbuilder;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.ClasspathBuilder;


public class TipiBuildClasspathTask extends BaseTipiClientTask {

	public void execute() throws BuildException {
		try {
			ClasspathBuilder cb = new ClasspathBuilder();
			cb.build(repository, extensions,getProject().getBaseDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

}