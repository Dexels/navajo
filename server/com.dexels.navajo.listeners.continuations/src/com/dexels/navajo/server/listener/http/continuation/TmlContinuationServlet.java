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

public class TmlContinuationServlet extends HttpServlet implements
		SchedulableServlet {

	private static final long serialVersionUID = -8645365233991777113L;

	private final static Logger logger = LoggerFactory
			.getLogger(TmlContinuationServlet.class);

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
			LocalClient localClient = getLocalClient();
			if ( localClient == null ) {
				localClient = getLocalClient(req);
			} 
			logger.debug("Servlet path info: "+req.getPathInfo());
			TmlRunnable instantiateRunnable = TmlRunnableBuilder.prepareRunnable(req,resp,localClient,null);
			if(instantiateRunnable!=null) {
			    Boolean prio =  (Boolean) instantiateRunnable.getAttribute("priority");
	            if (prio == null) 
	                prio = false;
				getTmlScheduler().submit(instantiateRunnable,  prio);
			}
		} catch (Throwable e) {
			if(e instanceof ServletException) {
				throw (ServletException)e;
			}
			logger.error("Servlet call failed dramatically", e);
		}
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
