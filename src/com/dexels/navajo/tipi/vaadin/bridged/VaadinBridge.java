package com.dexels.navajo.tipi.vaadin.bridged;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VaadinBridge extends HttpServlet {
	private static final long serialVersionUID = 8313772549433658256L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.err.println(">>>>>PAAAATH: "+pathInfo);
		if(pathInfo.startsWith("/VAADIN")) {
			System.err.println("VAADIN Spul:");
		}
		super.service(req, resp);
	}

	
}
