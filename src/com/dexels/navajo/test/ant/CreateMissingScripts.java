package com.dexels.navajo.test.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.test.ScriptTestCreator;


public class CreateMissingScripts extends org.apache.tools.ant.Task {
	
	private String scriptDir;
	private String testDir;
	
	public String getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(String scriptDir) {
		this.scriptDir = scriptDir;
	}

	public String getTestDir() {
		return testDir;
	}

	public void setTestDir(String testDir) {
		this.testDir = testDir;
	}

	public void execute() throws BuildException {
		ScriptTestCreator c = new ScriptTestCreator();
		File baseDir = getProject().getBaseDir();
		System.err.println("Project:"+baseDir);
		System.err.println("testDir: "+testDir);
		c.create(new File(baseDir,scriptDir), new File(baseDir,testDir));
		 
	}
}
