package com.dexels.navajo.server.listener.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;

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

	private static final long serialVersionUID = 1777790776513645459L;
	private String binaryPath;
	

	private final static Logger logger = LoggerFactory
			.getLogger(BinaryHttpServlet.class);
	
	public BinaryHttpServlet() {
	}

	public void destroy() {
	}

	protected void finalize() {
		 logger.info("In BinaryHttpServlet finalize(), thread = "
					+ Thread.currentThread().hashCode());
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
		if (handle == null) {
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
		if (!handleFile.exists()) {
			throw new ServletException("File not found");
		}

		Binary b = new Binary(handleFile, true);
		OutputStream outputStream = response.getOutputStream();
		b.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

}