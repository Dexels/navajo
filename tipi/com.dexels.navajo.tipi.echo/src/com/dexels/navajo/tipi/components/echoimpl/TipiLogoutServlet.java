package com.dexels.navajo.tipi.components.echoimpl;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TipiLogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -390870560218888712L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiLogoutServlet.class);
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  request.getSession().invalidate();
	  String destination =  request.getParameter("destination");
	  if(destination==null) {
		  destination = "/";
	  }
	  logger.info("Logout path: "+request.getContextPath());
	  response.sendRedirect(URLEncoder.encode(destination,"UTF-8"));
	 }

	}