package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.http.impl.BaseRequestImpl;

public class TmlRunnableBuilder {
	

	// Made static to indicate independence of fields
	public static TmlRunnable prepareRunnable(
			final HttpServletRequest req, HttpServletResponse resp, LocalClient localClient2)
			throws UnsupportedEncodingException, IOException {
		TmlContinuationRunner tmlRunner = (TmlContinuationRunner) req
				.getAttribute("tmlRunner");
		if (tmlRunner != null) {
			return null;
		}

		AsyncRequest request = constructRequest(req, resp, localClient2);
		TmlContinuationRunner instantiateRunnable = new TmlContinuationRunner(request,localClient2);
		req.setAttribute("tmlRunner", instantiateRunnable);
		instantiateRunnable.suspendContinuation();
		return instantiateRunnable;
	}

	
	// Made static to indicate independence of fields
	private static AsyncRequest constructRequest(final HttpServletRequest req,
			HttpServletResponse resp, final LocalClient lc)
			throws UnsupportedEncodingException, IOException {
		Object certObject = req.getAttribute("javax.servlet.request.X509Certificate");
		String contentEncoding = req.getHeader("Content-Encoding");
		String acceptEncoding = req.getHeader("Accept-Encoding");
		AsyncRequest request = null;
		if("POST".equals( req.getMethod())) {
			request = new BaseRequestImpl(lc, req, resp,acceptEncoding, contentEncoding, certObject);
		} else {
			Navajo in = TmlHttpServlet.constructFromRequest(req);
			request = new BaseRequestImpl(lc, in, req,resp);
		}
		return request;
	}
}
