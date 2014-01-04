package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.service.command.Descriptor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.githubosgi.GitRepositoryInstance;
import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;

public class Checkout extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(Checkout.class);
	
//	public void call(CommandSession session, @Descriptor(value = "The script to call") String scr) {

	
	@Descriptor(value = "Checkout a specific id") 
	public void checkout(String name,String objectid, String branchname) throws IOException {
		logger.info("Checkout application: {}",name);
		RepositoryInstance as = applications.get(name);
		if(!(as instanceof GitRepositoryInstance)) {
			throw new IOException("Can only pull from a Git application");
		}
		GitRepositoryInstance ha = (GitRepositoryInstance) as;
		try {
			ha.callCheckout(objectid,branchname);
		} catch (GitAPIException e) {
			throw new IOException(e);
		}
	}

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		verifyAuthorization(req, resp);
		String app = req.getParameter("app");
		String commitId = req.getParameter("commitId");
		String branchname = req.getParameter("branchName");
		if(app!=null) {
			checkout(app,commitId,branchname);
		}
		writeValueToJsonArray(resp.getOutputStream(),"checkout ok");
	}

	@Override
	public void build(RepositoryInstance a) throws IOException {
	}
}
