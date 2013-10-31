package com.dexels.navajo.tipi.dev.ant.projectbuilder;

import java.io.IOException;
import java.util.Map;

import com.dexels.navajo.tipi.dev.core.projectbuilder.ProjectBuilder;



public abstract class BaseTipiClientTask extends org.apache.tools.ant.Task {

	protected String repository = "";
	protected String deployment = "";
	protected String developmentRepository = "";

	
	
	public String getDeployment() {
		return deployment;
	}

	public void setDeployment(String deployment) {
		this.deployment = deployment;
	}

	public String getDevelopmentRepository() {
		return developmentRepository;
	}

	public void setDevelopmentRepository(String developmentRepository) {
		this.developmentRepository = developmentRepository;
	}
	
	protected Map<String,String> getTipiProperties() throws IOException {
		return ProjectBuilder.assembleTipi(getProject().getBaseDir());
	}

	protected String extensions = "";
	
	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
}
