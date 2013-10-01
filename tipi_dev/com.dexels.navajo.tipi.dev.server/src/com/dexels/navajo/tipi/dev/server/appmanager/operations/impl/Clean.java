package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public class Clean extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(Clean.class);
	
	public void clean(String name) throws IOException {
		logger.info("Cleaning application: {}",name);
		ApplicationStatus as = applications.get(name);
		build(as);
	}
	
	public void clean() throws IOException {
		logger.info("Cleaning all applications");
		for (ApplicationStatus a: applications.values()) {
			build(a);
		}
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String val = req.getParameter("app");
		if(val!=null) {
			clean(val);
		} else {
			clean();
		}
	}

	@Override
	public void build(ApplicationStatus a) throws IOException {
		File lib = new File(a.getAppFolder(),"lib");
		if(lib.exists()) {
			FileUtils.deleteQuietly(lib);
		}
		File xsd = new File(a.getAppFolder(),"xsd");
		if(xsd.exists()) {
			FileUtils.deleteQuietly(xsd);
		}
		File digest = new File(a.getAppFolder(),"resource/remotedigest.properties");
		if(digest.exists()) {
			FileUtils.deleteQuietly(digest);
		}
		
		File[] jnlps = a.getAppFolder().listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jnlp");
			}
		});		
		for (File file : jnlps) {
			FileUtils.deleteQuietly(file);
		}
	}
}
