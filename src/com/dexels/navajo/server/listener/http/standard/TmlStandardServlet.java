package com.dexels.navajo.server.listener.http.standard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.listener.http.SchedulableServlet;
import com.dexels.navajo.server.listener.http.SchedulerTools;
import com.dexels.navajo.server.listener.http.TmlScheduler;

public class TmlStandardServlet extends HttpServlet implements
		SchedulableServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3737722042000207161L;
	private TmlScheduler myScheduler = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean precheck = myScheduler.preCheckRequest(req);
		if (!precheck) {
			req.getInputStream().close();
			resp.getOutputStream().close();
			return;
		}
		System.err.println("Precheck complete");
		Navajo inputDoc = NavajoFactory.getInstance().createNavajo(req.getInputStream());
		req.getInputStream().close();

		Object certObject = req
				.getAttribute("javax.servlet.request.X509Certificate");
		String recvEncoding = req.getHeader("Content-Encoding");
		String sendEncoding = req.getHeader("Accept-Encoding");

		TmlRunnable tr = new TmlStandardRunner(req, inputDoc, resp,
				sendEncoding, recvEncoding, certObject);

		boolean check = getTmlScheduler().checkNavajo(inputDoc);
		if (!check) {
			resp.getOutputStream().close();
			return;
		}
		System.err.println("Check complete");

		System.err.println("Runnable created");
		getTmlScheduler().run(tr);
		System.err.println("Post finished");
	}

	@Override
	public TmlScheduler getTmlScheduler() {
		return myScheduler;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		setTmlScheduler(SchedulerTools.createScheduler(this));

	}

	@Override
	public void setTmlScheduler(TmlScheduler s) {
		myScheduler = s;
	}

}
