package com.dexels.navajo.tipi.ant.repositorybuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.dexels.navajo.maven.CreateMavenFromJar;

public class CreateMavenTask extends Task {

	private String groupId;
	private String artifactId;
	private String version;
	

	private String path;
	private String inputPath;
	
	public String getInputPath() {
		return inputPath;
	}


	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getArtifactId() {
		return artifactId;
	}


	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


//	public String getUrl() {
//		return url;
//	}
//
//
//	public void setUrl(String url) {
//		this.url = url;
//	}


	
	@Override
	public void execute() throws BuildException {
		CreateMavenFromJar j = new CreateMavenFromJar();
		try {
			j.createMavenFromJar(groupId, artifactId,new File(getProject().getBaseDir(),inputPath).toURI().toURL(), new File(getProject().getBaseDir(),path),version);
		} catch (Exception e) {
			throw new BuildException("CreateMavenFrom jar exception: "+e.getMessage(),e);
		}
	}

}
