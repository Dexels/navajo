package com.dexels.navajo.tipi.ant.projectbuilder;



public abstract class BaseTipiClientTask extends org.apache.tools.ant.Task {

	protected String repository = "";
	protected String developmentRepository = "";
	public String getDevelopmentRepository() {
		return developmentRepository;
	}

	public void setDevelopmentRepository(String developmentRepository) {
		this.developmentRepository = developmentRepository;
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
