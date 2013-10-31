package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.service.command.Descriptor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.dexels.navajo.tipi.dev.server.appmanager.GitApplicationStatus;

public class Checkout extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(Checkout.class);
	
//	public void call(CommandSession session, @Descriptor(value = "The script to call") String scr) {

	
	@Descriptor(value = "Checkout a specific id") 
	public void checkout(String name,String objectid, String branchname) throws IOException {
		logger.info("Checkout application: {}",name);
		ApplicationStatus as = applications.get(name);
		if(!(as instanceof GitApplicationStatus)) {
			throw new IOException("Can only pull from a Git application");
		}
		GitApplicationStatus ha = (GitApplicationStatus) as;
		try {
			ha.callCheckout(objectid,branchname);
		} catch (GitAPIException e) {
			throw new IOException(e);
		}
	}

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String app = req.getParameter("app");
		String commitId = req.getParameter("commitId");
		String branchname = req.getParameter("branchName");
		if(app!=null) {
			checkout(app,commitId,branchname);
		}
		writeValueToJsonArray(resp.getOutputStream(),"checkout ok");
	}

	@Override
	public void build(ApplicationStatus a) throws IOException {
	}
}
