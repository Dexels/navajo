package com.dexels.navajo.tipi.ant.projectbuilder;

import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.BaseJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;


public class TipiBuildLocalJnlp extends BaseTipiClientTask {
	public void execute() throws BuildException {
		try {
			LocalJnlpBuilder l = new LocalJnlpBuilder();
			l.build(repository,developmentRepository, extensions, getProject().getBaseDir(),getProject().getBaseDir().toURI().toURL().toString(),"Local.jnlp",null,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

}