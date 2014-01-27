package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.SchedulableServlet;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.script.api.TmlScheduler;

public class TmlContinuationMultitenantServlet extends HttpServlet implements
		SchedulableServlet {

	private static final long serialVersionUID = -8645365233991777113L;

	private final static Logger logger = LoggerFactory
			.getLogger(TmlContinuationMultitenantServlet.class);

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
	public static final String COMPRESS_NONE = "";

	private LocalClient localClient;
	private TmlScheduler tmlScheduler;

	// private boolean jmxRegistered = false;

	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}

	public void setTmlScheduler(TmlScheduler scheduler) {
		this.tmlScheduler = scheduler;
	}

	public void clearTmlScheduler(TmlScheduler scheduler) {
		this.tmlScheduler = null;
	}

	public void activate() {
		logger.info("Continuation servlet component activated");
	}

	public void deactivate() {
		logger.info("Continuation servlet component deactivated");
	}

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
			TmlRunnable instantiateRunnable = TmlRunnableBuilder.prepareRunnable(req,resp,localClient,instance);
			if(instantiateRunnable!=null) {
				getTmlScheduler().submit(instantiateRunnable, false);
			}
		} catch (Throwable e) {
			if(e instanceof ServletException) {
				throw (ServletException)e;
			}
			logger.error("Servlet call failed dramatically", e);
		}
	}

	private String determineInstanceFromRequest(final HttpServletRequest req) {
		String pathinfo = req.getPathInfo();
		if(pathinfo.startsWith("/")) {
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
//				resp.sendError(500,
//						"No local client registered in servlet context");
			throw new ServletException("No local client registered in servlet context");
		}
		return lc;
	}

	@Override
	public TmlScheduler getTmlScheduler() throws ServletException {
		if (tmlScheduler != null) {
			return tmlScheduler;
		}
		TmlScheduler attribute = (TmlScheduler) getServletContext()
				.getAttribute("tmlScheduler");
		if(attribute==null) {
			throw new ServletException("Can not use scheduling servlet: No scheduler found.");
		}
		return attribute;
	}



}
