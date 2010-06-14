package com.dexels.navajo.tipi;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TipiRedirectServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String servletPath = request.getServletPath();
		String appStoreUrl = getServletContext().getInitParameter("appUrl");
//		String appFolder = getServletContext().getInitParameter("appFolder");
	    String forwardAddress = appStoreUrl+request.getServletPath();
			RequestDispatcher dispatcher = request.getRequestDispatcher(forwardAddress);
		    dispatcher.forward(request, response);

//		    URL u = new URL(forwardAddress);
//		    URLConnection uc = u.openConnection();
//		    Map<String, List<String>> s = uc.getHeaderFields();
//		    for (Entry<String,List<String>> e: s.entrySet()) {
//			}
//		    
//		String applicationPath = servletPath.substring(1,servletPath.lastIndexOf('/'));
//
//		String myAppPath = appFolder+applicationPath;
//		System.err.println("Resolved app path: "+myAppPath);
//		String myAppUrl = appStoreUrl+applicationPath;
//		System.err.println("Resolved app url: "+myAppUrl);
		}
}
