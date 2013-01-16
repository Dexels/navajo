package com.dexels.navajo.server.listener.http.external;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.listener.http.standard.TmlStandardServlet;
import com.dexels.navajo.server.listener.http.wrapper.NavajoRequestWrapper;
import com.dexels.navajo.server.listener.http.wrapper.NavajoResponseWrapper;
import com.dexels.navajo.server.listener.http.wrapper.identity.IdentityRequestWrapper;
import com.dexels.navajo.server.listener.http.wrapper.identity.IdentityResponseWrapper;

public class NavajoFilterServlet extends TmlStandardServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7843782840626326460L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoFilterServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	protected void doProcess(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException {


		Navajo input = buildRequest(getInitParameter("inputFilterClass"), req);
//		boolean check = getTmlScheduler().checkNavajo(input);

		LocalClient lc = (LocalClient) getServletContext().getAttribute("localClient");
		if(lc==null) {
			resp.sendError(500,"No navajocontext configured (in NavajoFilterServlet)");
			return;
		}
		try {
			Navajo output = lc.call(input);
			processResponse(req, input, output, resp);

		} catch (FatalException e1) {
			throw new ServletException(e1);
		}
	}

	private Navajo buildRequest(String inFilter, HttpServletRequest request)
			throws ServletException, IOException {
		NavajoRequestWrapper nrw = getRequestWrapper(inFilter);
		if (nrw == null) {
			nrw = new IdentityRequestWrapper();
		}
		return nrw.processRequestFilter(request);
	}

	/**
	 * @param originalRequest
	 * @param wrappedRequest
	 *            could be null, if no inputFilter has been supplied
	 * @param wrappedResponse
	 *            could be null, if no outputFilter has been supplied
	 * @param originalResponse
	 * @throws IOException
	 */
	private void processResponse(HttpServletRequest originalRequest,
			Navajo indoc, Navajo outdoc, HttpServletResponse originalResponse)
			throws IOException {
		NavajoResponseWrapper nrw = getResponseWrapper(getInitParameter("outputFilterClass"));
		if (nrw == null) {
			nrw = new IdentityResponseWrapper();
		}
		nrw.processResponse(originalRequest, indoc, outdoc, originalResponse);
	}

	private NavajoRequestWrapper getRequestWrapper(String inFilter) {
		try {
			Class<? extends NavajoRequestWrapper> rwrapperClass = (Class<? extends NavajoRequestWrapper>) Class
					.forName(inFilter);
			return rwrapperClass.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("Error: ", e);
		} catch (InstantiationException e) {
			logger.error("Error: ", e);
		} catch (IllegalAccessException e) {
			logger.error("Error: ", e);
		}
		return null;
	}

	private NavajoResponseWrapper getResponseWrapper(String outFilter) {
		try {
			Class<? extends NavajoResponseWrapper> rwrapperClass = (Class<? extends NavajoResponseWrapper>) Class
					.forName(outFilter);
			return rwrapperClass.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("Error: ", e);
		} catch (InstantiationException e) {
			logger.error("Error: ", e);
		} catch (IllegalAccessException e) {
			logger.error("Error: ", e);
		}
		return null;
	}
}
