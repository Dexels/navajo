package com.dexels.navajo.tipi.ant.projectbuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.BaseJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.JnlpDeployer;
import com.dexels.navajo.tipi.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.projectbuilder.XsdBuilder;


public class TipiBuildDeployJnlp  extends org.apache.tools.ant.Task {
	
	private String codebase;
	private String baseDir;
	private String deployPath;
	public String getDeployPath() {
		return deployPath;
	}


	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}


	public String getBaseDir() {
		return baseDir;
	}


	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}


	public String getCodebase() {
		return codebase;
	}


	public void setCodebase(String codebase) {
		this.codebase = codebase;
	}


	public void execute() throws BuildException {
		try {
			JnlpDeployer jd = new JnlpDeployer();
			
			File baseDir = new File(getBaseDir());
			File deployDir = new File(baseDir,deployPath);
			if(!deployDir.exists()) {
				deployDir.mkdirs();
			}
			File[] rootFiles = baseDir.listFiles();
			for (File file : rootFiles) {
				if(file.isFile() && file.getName().endsWith(".jnlp")) {
					File destination = new File(deployDir,file.getName());
					System.err.println("Deploying to destination: "+destination);
					jd.deploy(file, destination, codebase);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	

}