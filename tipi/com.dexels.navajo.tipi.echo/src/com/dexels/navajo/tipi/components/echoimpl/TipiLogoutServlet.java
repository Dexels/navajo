/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import java.io.IOException;

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
		  response.sendRedirect(destination);
	 }

	}