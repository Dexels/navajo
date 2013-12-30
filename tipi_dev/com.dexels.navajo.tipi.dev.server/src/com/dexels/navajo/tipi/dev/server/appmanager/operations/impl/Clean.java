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

import com.dexels.githubosgi.GitRepositoryInstance;
import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;

public class Clean extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(Clean.class);
	
	public void clean(String name) throws IOException {
		logger.info("Cleaning application: {}",name);
		RepositoryInstance as = applications.get(name);
		build(as);
	}
	
	public void clean() throws IOException {
		logger.info("Cleaning all applications");
		for (RepositoryInstance a: applications.values()) {
			build(a);
		}
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		verifyAuthorization(req, resp);
		String val = req.getParameter("app");
		if(val!=null) {
			clean(val);
		} else {
			clean();
		}
		writeValueToJsonArray(resp.getOutputStream(),"clean ok");
		
	}

	@Override
	public void build(RepositoryInstance a) throws IOException {
		if(a instanceof GitRepositoryInstance) {
			callGitClean((GitRepositoryInstance)a);
		}
		File lib = new File(a.getRepositoryFolder(),"lib");
		if(lib.exists()) {
			FileUtils.deleteQuietly(lib);
		}
		File xsd = new File(a.getRepositoryFolder(),"xsd");
		if(xsd.exists()) {
			FileUtils.deleteQuietly(xsd);
		}
		File digest = new File(a.getRepositoryFolder(),"resource/remotedigest.properties");
		if(digest.exists()) {
			FileUtils.deleteQuietly(digest);
		}
		
		File[] jnlps = a.getRepositoryFolder().listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jnlp");
			}
		});		
		for (File file : jnlps) {
			FileUtils.deleteQuietly(file);
		}
	}

	private void callGitClean(GitRepositoryInstance a) throws IOException {
		try {
			a.callClean();
		} catch (GitAPIException e) {
			throw new IOException("Error git-cleaning!",e);
		}
		
	}
}
