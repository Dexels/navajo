package com.dexels.navajo.tipi;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;

/**
 * Servlet implementation class TipiCreateServlet
 */
public class TipiCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String template = request.getParameter("template");
		if(template==null) {
			template = (String) getServletContext().getInitParameter("template");
		}
		String repository =  (String) getServletContext().getInitParameter("repository");
		String developmentRepository =  (String) getServletContext().getInitParameter("developmentRepository");

		String name = request.getParameter("name");
		ClientActions.downloadZippedDemoFiles(developmentRepository,repository, new File(getServletContext().getRealPath(name)),template);
		response.sendRedirect("applications.jsp");
	}

}
