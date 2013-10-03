package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.dexels.navajo.tipi.dev.server.appmanager.GitApplicationStatus;

public class Pull extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(Pull.class);
	
	public void pull(String name) throws IOException {
		logger.info("Pull application: {}",name);
		ApplicationStatus as = applications.get(name);
		build(as);
	}
	
	public void pull() throws IOException {
		logger.info("Pull all applications");
		for (ApplicationStatus a: applications.values()) {
			build(a);
		}
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String val = req.getParameter("app");
		if(val!=null) {
			pull(val);
		} else {
			pull();
		}
	}

	@Override
	public void build(ApplicationStatus a) throws IOException {
		if(!(a instanceof GitApplicationStatus)) {
			throw new IOException("Can only pull from a Git application");
		}
		GitApplicationStatus ha = (GitApplicationStatus) a;
		try {
			ha.callPull();
		} catch (GitAPIException e) {
			throw new IOException(e);
		}
	}
}
