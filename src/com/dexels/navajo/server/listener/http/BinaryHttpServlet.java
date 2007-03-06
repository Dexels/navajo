package com.dexels.navajo.server.listener.http;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 *
 * $Id$
 *
 */

/**
 * This servlet handles HTTP POST requests. The HTTP POST body is assumed to
 * contain a TML document. The TML document is processed by the dispatcher the
 * resulting TML document is send back as a reply.
 * 
 */

public final class BinaryHttpServlet extends HttpServlet {

	private String binaryPath;

	public BinaryHttpServlet() {
	}

	public void destroy() {
	}

	protected void finalize() {
		System.err.println("In BinaryHttpServlet finalize(), thread = "
				+ Thread.currentThread().hashCode());
		// logger.log(Priority.INFO, "In TmlHttpServlet finalize()");
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String b = config.getInitParameter("binaryPath");

		if (b == null) {
			binaryPath = System.getProperty("java.io.tmpdir");
		} else {
			binaryPath = System.getProperty("java.io.tmpdir") + "/" + b;

		}

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public final void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String handle = request.getParameter("handle");
		String instance = request.getParameter("applicationInstance");
		File binaryFolder = new File(binaryPath);
		File handleFile = null;
		if (handle==null) {
			throw new ServletException("No handle present!");
		}
		if (instance != null && !"".equals(instance)) {
			File instanceFolder = new File(binaryFolder, instance);
			if (!instanceFolder.exists()) {
				throw new ServletException("Missing instance folder!");
			}
			handleFile = new File(instanceFolder, handle);

		} else {
			handleFile = new File(binaryFolder, handle);
		}
		if (handle.indexOf("/") != -1) {
			// security check, otherwise you could navigate outside the tmp dir.
			throw new ServletException("Handle error");
		}
		if (handleFile == null || !handleFile.exists()) {
			throw new ServletException("File not found");
		}

		Binary b = new Binary(handleFile, true);
		OutputStream outputStream = response.getOutputStream();
		b.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

}