package com.dexels.navajo.resource.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReloadDataSourcesServlet extends HttpServlet {

	private static final long serialVersionUID = 1079149886423293937L;
	private ResourceManager manager;

	public void setResourceManager(ResourceManager r) {
		manager = r;
	}

	/**
	 * @param r
	 *            the resource manager to remove
	 */
	public void removeResourceManager(ResourceManager r) {
		manager = null;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		reload();
		resp.sendRedirect("index.jsp");
	}
	
	private void reload() {
		if (manager != null) {
			manager.unloadDataSources();
			manager.setupResources();

		}
	}

	
}
