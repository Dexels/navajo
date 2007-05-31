package com.dexels.navajo.tipi.components.echoimpl;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;


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