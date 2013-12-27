package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;

public class CleanRepo extends BaseOperation implements AppStoreOperation {

	
	
	private static final long serialVersionUID = -3363914555886806226L;


	public void cleanrepo() throws IOException {
		File repo = new File(getRepositoryManager().getRepositoryFolder(), "repo");
		FileUtils.deleteQuietly(repo);
	}
	
	@Override
	public void build(RepositoryInstance a) throws IOException {
		//
	}

	@Override
	// Maybe we should protect this one, it is kind of destructive
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		verifyAuthorization(req, resp);
		cleanrepo();
		writeValueToJsonArray(resp.getOutputStream(),"cleanrepo  ok");

	}
}
