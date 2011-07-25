package com.dexels.navajo.tipi.vaadin.application.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.GAEApplicationServlet;

public class TipiVaadinGaeServlet extends GAEApplicationServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8125011483209557703L;

	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		TipiVaadinApplication tipiApplication = (TipiVaadinApplication) super.getNewApplication(request);
		tipiApplication.setServletContext(getServletContext());
		// add request data?
		return tipiApplication;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		super.service(request, response);
	}
	
	

}
