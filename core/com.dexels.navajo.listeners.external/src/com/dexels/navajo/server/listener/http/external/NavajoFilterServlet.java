/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.listener.http.external;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.listener.http.standard.TmlStandardServlet;
import com.dexels.navajo.server.listener.http.wrapper.NavajoRequestWrapper;
import com.dexels.navajo.server.listener.http.wrapper.NavajoResponseWrapper;
import com.dexels.navajo.server.listener.http.wrapper.identity.IdentityRequestWrapper;

public class NavajoFilterServlet extends TmlStandardServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 7843782840626326460L;

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoFilterServlet.class);

	private NavajoResponseWrapper responseWrapper;

	private NavajoRequestWrapper requestWrapper;
	private LocalClient localClient;
	private final Map<String,LocalClient> localClients = new HashMap<String, LocalClient>();

	@Override
	protected void service(final HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String instance = determineInstanceFromRequest(req);
			LocalClient localClient = getLocalClient();
			if ( localClient == null ) {
				localClient = getLocalClient(req);
			}
			logger.info("Instance determined from request: "+instance);
			Navajo input = buildRequest(getInitParameter("inputFilterClass"), req);
			Navajo result = localClient.call(instance, input);
			responseWrapper.processResponse(req, input, result, resp);

		} catch (Throwable e) {
			if(e instanceof ServletException) {
				throw (ServletException)e;
			}
			logger.error("Servlet call failed dramatically", e);
		}
	}

	public void addLocalClient(LocalClient localClient, Map<String,Object> settings) {
		String name = (String) settings.get("instance");
		if (name==null) {
			this.localClient  = localClient;
		} else {
			this.localClients.put(name, localClient);
		}

	}



	public void removeLocalClient(LocalClient localClient, Map<String,Object> settings) {
		String name = (String) settings.get("instance");
		if (name==null) {
			this.localClient  = null;
		} else {
			this.localClients.remove(name);
		}
//		this.localClient = null;
	}

	private Navajo buildRequest(String inFilter, HttpServletRequest request)
			throws ServletException, IOException {
		NavajoRequestWrapper nrw = getRequestWrapper(inFilter);
		if (nrw == null) {
			nrw = new IdentityRequestWrapper();
		}
		return nrw.processRequestFilter(request);
	}
	private String determineInstanceFromRequest(final HttpServletRequest req) {
		String requestInstance = req.getHeader("X-Navajo-Instance");
		if(requestInstance!=null) {
			return requestInstance;
		}
		String pathinfo = req.getPathInfo();
		if(pathinfo==null) {
			logger.warn("Laszlo call: No instance could be determined");
			return null;
		}
		if(pathinfo.length() > 0 && pathinfo.charAt(0) == '/') {
			pathinfo = pathinfo.substring(1);
		}
		String instance = null;
		if(pathinfo.indexOf('/')!=-1) {
			instance = pathinfo.substring(0, pathinfo.indexOf('/'));
		} else {
			instance = pathinfo;
		}
		return instance;
	}



	protected LocalClient getLocalClient(final HttpServletRequest req)
			throws ServletException {

		LocalClient tempClient = localClient;
		if (localClient == null) {
			tempClient = (LocalClient) req.getServletContext()
					.getAttribute("localClient");
		}

		final LocalClient lc = tempClient;
		if (lc == null) {
			logger.error("No localclient found");
			throw new ServletException("No local client registered in servlet context");
		}

        return lc;
	}


	public LocalClient getLocalClient() {
		return localClient;
	}

	private NavajoRequestWrapper getRequestWrapper(String inFilter) {

		if(requestWrapper!=null) {
			return this.requestWrapper;
		}

		try {
			Class<?> rwrapperClass = Class.forName(inFilter);
			return (NavajoRequestWrapper) rwrapperClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			logger.error("Error: ", e);
		}

		return null;
	}

	public void setRequestWrapper(NavajoRequestWrapper nrw) {
		this.requestWrapper = nrw;
	}

	public void clearRequestWrapper(NavajoRequestWrapper nrw) {
		this.requestWrapper = null;
	}

	public void setResponseWrapper(NavajoResponseWrapper nrw) {
		this.responseWrapper = nrw;
	}

	public void clearResponseWrapper(NavajoResponseWrapper nrw) {
		this.responseWrapper = null;
	}

}
