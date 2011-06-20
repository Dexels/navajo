package com.dexels.navajo.server.listener.http.continuation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.listener.http.SchedulableServlet;
import com.dexels.navajo.server.listener.http.SchedulerTools;
import com.dexels.navajo.server.listener.http.TmlScheduler;
import com.jcraft.jzlib.ZInputStream;

public class TmlContinuationServlet extends HttpServlet implements SchedulableServlet {

	private static final long serialVersionUID = -8645365233991777113L;

	private TmlScheduler myScheduler = null;

	
	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
	public static final String COMPRESS_NONE = "";

	
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


	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		TmlContinuationRunner tmlRunner = (TmlContinuationRunner) req.getAttribute("tmlRunner");
		if(tmlRunner!=null) {
//			tmlRunner.setResponse(resp);
			tmlRunner.endTransaction();
			return;
		}
		boolean precheck = myScheduler.preCheckRequest(req);
		if(!precheck) {
			req.getInputStream().close();
			resp.getOutputStream().close();
			return;
		}
		Navajo inputDoc = parseInputNavajo(req);

	  	  Object certObject = req.getAttribute( "javax.servlet.request.X509Certificate");
			String recvEncoding = req.getHeader("Content-Encoding");
			String sendEncoding = req.getHeader("Accept-Encoding");

			boolean check = getTmlScheduler().checkNavajo(inputDoc);
			if(!check) {
				resp.getOutputStream().close();
				return;
			}

			// Resolve remote address first. To prevent random IP addresses when trying to
			// resolve it at a later time (???).
			String remoteAddress = req.getRemoteAddr();
			TmlContinuationRunner tr = new TmlContinuationRunner(req,inputDoc, resp,  sendEncoding, recvEncoding, certObject);
			req.setAttribute("tmlRunner", tr);
		
		tr.suspendContinuation();
		getTmlScheduler().submit(tr,false);
	}

	
	protected final Navajo parseInputNavajo(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		InputStream is = request.getInputStream();
		String sendEncoding = request.getHeader("Accept-Encoding");

		BufferedReader r;
		  Navajo in = null;
		if (sendEncoding != null && sendEncoding.equals(COMPRESS_JZLIB)) {
			r = new BufferedReader(new java.io.InputStreamReader(
					new ZInputStream(is)));
		} else if (sendEncoding != null && sendEncoding.equals(COMPRESS_GZIP)) {
			r = new BufferedReader(new java.io.InputStreamReader(
					new java.util.zip.GZIPInputStream(is), "UTF-8"));
		} else {
			r = new BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
		}
		in = NavajoFactory.getInstance().createNavajo(r);

		  if (in == null) {
			  throw new IOException("Invalid request.");
		  }

		  Header header = in.getHeader();
		  if (header == null) {
			  throw new IOException("Empty Navajo header.");
		  }

		  is.close();

		return in;
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
