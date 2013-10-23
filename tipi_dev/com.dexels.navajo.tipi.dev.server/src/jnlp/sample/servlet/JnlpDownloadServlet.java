/*
 * @(#)JnlpDownloadServlet.java	1.10 07/03/15
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

package jnlp.sample.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jnlp.sample.servlet.impl.FileSystemResourceResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;

/**
 * This Servlet class is an implementation of JNLP Specification's Download
 * Protocols.
 * 
 * All requests to this servlet is in the form of HTTP GET commands. The
 * parameters that are needed are:
 * <ul>
 * <li><code>arch</code>,
 * <li><code>os</code>,
 * <li><code>locale</code>,
 * <li><code>version-id</code> or <code>platform-version-id</code>,
 * <li><code>current-version-id</code>,
 * <li>code>known-platforms</code>
 * </ul>
 * <p>
 * 
 * @version 1.8 01/23/03
 */
public class JnlpDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -351516869579920417L;

	// Localization
	private static ResourceBundle _resourceBundle = null;

	// Servlet configuration
	private static final String PARAM_JNLP_EXTENSION = "jnlp-extension";
	private static final String PARAM_JAR_EXTENSION = "jar-extension";

	// Servlet configuration
	
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpDownloadServlet.class);
	
	private JnlpFileHandler _jnlpFileHandler = null;
//	private JarDiffHandler _jarDiffHandler = null;
	private ResourceCatalog _resourceCatalog = null;
	private ApplicationManager applicationManager;
	//private String appFolder;

	private String basePath;

	
	public JnlpDownloadServlet() {
	}
	/** Initialize servlet */
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);
			// Setup logging
//			Enumeration<String> en = config.getInitParameterNames();
//			while (en.hasMoreElements()) {
//				String pp = (String) en.nextElement();
//			}
			// String appFolder = (String)
			// config.getServletContext().getInitParameter("appFolder");
			File baseDir = getAppFolder();
			config.getServletContext().setAttribute("resourceResolver",new FileSystemResourceResolver(baseDir,basePath));
			// Get extension from Servlet configuration, or use default
			JnlpResource.setDefaultExtensions(
					config.getInitParameter(PARAM_JNLP_EXTENSION),
					config.getInitParameter(PARAM_JAR_EXTENSION));

			_jnlpFileHandler = new JnlpFileHandler(config.getServletContext(), logger,applicationManager);
//			_jarDiffHandler = new JarDiffHandler(config.getServletContext(), logger);
			_resourceCatalog = new ResourceCatalog(config.getServletContext(), logger);
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	public static synchronized ResourceBundle getResourceBundle() {
		if (_resourceBundle == null) {
			_resourceBundle = ResourceBundle
					.getBundle("jnlp/sample/servlet/resources/strings", Locale.getDefault(),JnlpDownloadServlet.class.getClassLoader());
		}
		return _resourceBundle;
	}

	private File getAppFolder() {
		return applicationManager.getAppsFolder();
	}

	public ApplicationManager getApplicationManager() {
		return applicationManager;
	}

	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public void clearApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = null;
	}

	public void activate(Map<String,Object> settings) {
		this.basePath = (String) settings.get("tipi.base.path");
//		this.codebase = (String) settings.get("tipi.codebase");
		if(basePath==null) {
			basePath = "/apps";
		}
	}
	public void deactivate() {
		
	}
	@Override
	public void doHead(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response, true);
	}

	/**
	 * We handle get requests too - eventhough the spec. only requeres POST
	 * requests
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response, false);
		
	}

	private void handleRequest(HttpServletRequest request,
			HttpServletResponse response, boolean isHead) throws IOException {
		String requestStr = request.getRequestURI();
		// logger.info("Request: "+request.getRequestURI()+" query: "+request.getQueryString());
		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
//			logger.info("HTTP Key: "+key+" header: "+request.getHeader(key));
		}
		// TipiAdminServlet.buildIfNecessary(request,getAppFolder(),getServletContext());
//		Map en = request.getParameterMap();
//		for (Object key : en.keySet()) {
//			Object value = en.get(key);
//			if (value instanceof Object[]) {
//				Object[] vals = (Object[]) value;
				// for (Object object : vals) {
				// logger.info("Value element: "+object);
				// }
//			} else {
//				logger.debug("Param: " + key + " value: " + en.get(key));
//			}
//		}
		if (request.getQueryString() != null)
			requestStr += "?" + request.getQueryString().trim();

		// Parse HTTP request
		DownloadRequest dreq = new DownloadRequest(getServletContext(), request);
			logger.debug("servlet.log.info.request", requestStr);
			logger.debug("servlet.log.info.useragent",
					request.getHeader("User-Agent"));
			logger.debug(dreq.toString());

		long ifModifiedSince = request.getDateHeader("If-Modified-Since");

		// Check if it is a valid request
		try {
			// Check if the request is valid
			validateRequest(dreq);

			// Decide what resource to return
			JnlpResource jnlpres = locateResource(dreq);
			logger.debug("JnlpResource: " + jnlpres);

				logger.debug("servlet.log.info.goodrequest",
						jnlpres.getPath());

			DownloadResponse dres = null;

			if (isHead) {

				int cl = jnlpres.getResource().openConnection()
						.getContentLength();

				// head request response
				dres = DownloadResponse.getHeadRequestResponse(
						jnlpres.getMimeType(), jnlpres.getVersionId(),
						jnlpres.getLastModified(), cl);

			} else if (ifModifiedSince != -1
					&& (ifModifiedSince / 1000) >= (jnlpres.getLastModified() / 1000)) {
				// We divide the value returned by getLastModified here by 1000
				// because if protocol is HTTP, last 3 digits will always be
				// zero. However, if protocol is JNDI, that's not the case.
				// so we divide the value by 1000 to remove the last 3 digits
				// before comparison

				// return 304 not modified if possible
				logger.debug("return 304 Not modified");
				dres = DownloadResponse.getNotModifiedResponse();

			} else {

				// Return selected resource
				dres = constructResponse(jnlpres, dreq);
			}
			// logger.info("Resetting encoding!!!");
			// response.setHeader("Content-Encoding", "");
			dres.sendRespond(response);

		} catch (ErrorResponseException ere) {
				logger.info("servlet.log.info.badrequest:"+ requestStr,ere);
				logger.debug("Response: " + ere.toString());
			// Return response from exception
			ere.getDownloadResponse().sendRespond(response);
		} catch (Throwable e) {
			logger.error("servlet.log.fatal.internalerror", e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Make sure that it is a valid request. This is also the place to implement
	 * the reverse IP lookup
	 */
	private void validateRequest(DownloadRequest dreq)
			throws ErrorResponseException {
		String path = dreq.getPath();
		if (path.endsWith(ResourceCatalog.VERSION_XML_FILENAME)
				|| path.indexOf("__") != -1) {
			throw new ErrorResponseException(
					DownloadResponse.getNoContentResponse());
		}
	}

	/**
	 * Interprets the download request and convert it into a resource that is
	 * part of the Web Archive.
	 */
	private JnlpResource locateResource(DownloadRequest dreq)
			throws IOException, ErrorResponseException {
		if (dreq.getVersion() == null) {
			return handleBasicDownload(dreq);
		} else {
			return handleVersionRequest(dreq);
		}
	}

	private JnlpResource handleBasicDownload(DownloadRequest dreq)
			throws ErrorResponseException, IOException {
		logger.debug("Basic Protocol lookup");
		// Do not return directory names for basic protocol
		if (dreq.getPath() == null || dreq.getPath().endsWith("/")) {
			throw new ErrorResponseException(
					DownloadResponse.getNoContentResponse());
		}
		// Lookup resource
		JnlpResource jnlpres = new JnlpResource(getServletContext(),
				dreq.getPath());
		if (!jnlpres.exists()) {
			throw new ErrorResponseException(
					DownloadResponse.getNoContentResponse());
		}
		return jnlpres;
	}

	private JnlpResource handleVersionRequest(DownloadRequest dreq)
			throws IOException, ErrorResponseException {
		logger.debug("Version-based/Extension based lookup");
		return _resourceCatalog.lookupResource(dreq);
	}

	/**
	 * Given a DownloadPath and a DownloadRequest, it constructs the data stream
	 * to return to the requester
	 */
	private DownloadResponse constructResponse(JnlpResource jnlpres,
			DownloadRequest dreq) throws IOException {
//		String path = jnlpres.getPath();
		if (jnlpres.isJnlpFile()) {
			// It is a JNLP file. It need to be macro-expanded, so it is handled
			// differently
			boolean supportQuery = JarDiffHandler.isJavawsVersion(dreq, "1.5+");

			// only support query string in href for 1.5 and above
			if (supportQuery) {
				return _jnlpFileHandler.getJnlpFileEx(jnlpres, dreq);
			} else {
				return _jnlpFileHandler.getJnlpFile(jnlpres, dreq);
			}
		}


		// check and see if we can use pack resource
		JnlpResource jr = new JnlpResource(getServletContext(),
				jnlpres.getName(), jnlpres.getVersionId(), jnlpres.getOSList(),
				jnlpres.getArchList(), jnlpres.getLocaleList(),
				jnlpres.getPath(), jnlpres.getReturnVersionId(),
				dreq.getEncoding());


		// Return WAR file resource
		return DownloadResponse
				.getFileDownloadResponse(jr.getResource(), jr.getMimeType(),
						jr.getLastModified(), jr.getReturnVersionId());
	}
}
