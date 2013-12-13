package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.githubosgi.GitRepositoryInstance;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;

public class Pull extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(Pull.class);
	
	public void pull(String name) throws IOException {
		logger.info("Pull application: {}",name);
		RepositoryInstance as = applications.get(name);
		build(as);
	}
	
	public void pull() throws IOException {
		logger.info("Pull all applications");
		for (RepositoryInstance a: applications.values()) {
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
		writeValueToJsonArray(resp.getOutputStream(),"pull ok");

	}

	@Override
	public void build(RepositoryInstance a) throws IOException {
		if(!(a instanceof GitRepositoryInstance)) {
			throw new IOException("Can only pull from a Git application");
		}
		GitRepositoryInstance ha = (GitRepositoryInstance) a;
		try {
			ha.callPull();
		} catch (GitAPIException e) {
			throw new IOException(e);
		}
	}
}
