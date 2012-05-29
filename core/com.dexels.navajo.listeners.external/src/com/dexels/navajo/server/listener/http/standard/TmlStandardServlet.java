package com.dexels.navajo.server.listener.http.standard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.listener.http.impl.BaseRequestImpl;

public class TmlStandardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3737722042000207161L;
	private TmlScheduler myScheduler = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final LocalClient lc = (LocalClient) req.getServletContext().getAttribute("localClient");
		if(lc==null) {
			resp.sendError(500, "No local client registered in servlet context");
			return;
		}		
		boolean precheck = myScheduler.preCheckRequest(req);
		if (!precheck) {
			req.getInputStream().close();
			resp.getOutputStream().close();
			return;
		}
		
		Object certObject = req
				.getAttribute("javax.servlet.request.X509Certificate");
		String recvEncoding = req.getHeader("Content-Encoding");
		String sendEncoding = req.getHeader("Accept-Encoding");
		AsyncRequest request = new BaseRequestImpl(lc,req, resp, sendEncoding, recvEncoding, certObject) {

			@Override
			public TmlRunnable instantiateRunnable() {
				return new TmlStandardRunner(this,lc);
			}};

//		Navajo inputDoc = NavajoFactory.getInstance().createNavajo(req.getInputStream());
//		req.getInputStream().close();



		boolean check = getTmlScheduler().checkNavajo(request.getInputDocument());
		if (!check) {
			resp.getOutputStream().close();
			return;
		}
		getTmlScheduler().run(request.instantiateRunnable());
	}

	public TmlScheduler getTmlScheduler() {
		return myScheduler;
	}
}
