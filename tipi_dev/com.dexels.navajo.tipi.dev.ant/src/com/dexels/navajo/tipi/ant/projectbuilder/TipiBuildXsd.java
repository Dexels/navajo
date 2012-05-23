package com.dexels.navajo.tipi.ant.projectbuilder;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;


public class TipiBuildXsd extends BaseTipiClientTask {

	public void execute() throws BuildException {
		try {
			XsdBuilder xsd = new XsdBuilder();
			xsd.build(repository,repository+"Extensions/", extensions,getProject().getBaseDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

}