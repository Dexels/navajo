package com.dexels.navajo.tipi.components.echoimpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */
public class TipiLogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -390870560218888712L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  request.getSession().invalidate();
	  String destination =  request.getParameter("destination");
	  if(destination==null) {
		  destination = "/";
	  }
	  System.err.println("Logout path: "+request.getContextPath());
	  response.sendRedirect(destination);
	 }

	}