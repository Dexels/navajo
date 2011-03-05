package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.listener.http.SchedulableServlet;
import com.dexels.navajo.server.listener.http.SchedulerTools;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.dexels.navajo.server.listener.http.standard.TmlStandardRunner;

public class TmlContinuationServlet extends HttpServlet implements SchedulableServlet {

	private static final long serialVersionUID = -8645365233991777113L;

	private TmlScheduler myScheduler = null;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.err.println("In ContinuationServlet init()");
		setTmlScheduler(SchedulerTools.createScheduler(this));
		JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "TmlCometServlet");
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		TmlRunnable tmlRunner = (TmlRunnable) req.getAttribute("tmlRunner");
		if(tmlRunner!=null) {
			System.err.println("RESUMING CONTINUATION.");
			tmlRunner.endTransaction();
			return;
		}
		boolean precheck = myScheduler.preCheckRequest(req);
		if(!precheck) {
			req.getInputStream().close();
			resp.getOutputStream().close();
			return;
		}
		System.err.println("Precheck complete");
		Navajo inputDoc = NavajoFactory.getInstance().createNavajo(req.getInputStream());
		req.getInputStream().close();

	  	  Object certObject = req.getAttribute( "javax.servlet.request.X509Certificate");
			String recvEncoding = req.getHeader("Content-Encoding");
			String sendEncoding = req.getHeader("Accept-Encoding");

			boolean check = getTmlScheduler().checkNavajo(inputDoc);
			if(!check) {
				resp.getOutputStream().close();
				return;
			}
			System.err.println("Check complete");

			TmlContinuationRunner tr = new TmlContinuationRunner(req,inputDoc, resp,  sendEncoding, recvEncoding, certObject);
			tr.setTmlScheduler(getTmlScheduler());
			req.setAttribute("tmlRunner", tr);
		
		System.err.println("Runnable created: Scheduler: "+getTmlScheduler());
		getTmlScheduler().submit(tr,false);
		tr.suspendContinuation();
		System.err.println("Post finished & servlet suspended");
		
		
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
