package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.SchedulableServlet;
import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.http.impl.BaseRequestImpl;

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

//	public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
//		final Continuation continuation = ContinuationSupport
//				.getContinuation(request);
//
//		try {
//			doPost(request,response);
//		} catch (ServletException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		if(true) {
//			return;
//		}
//		// if this is not a timeout
//		if (continuation.isExpired()) {
//			try {
//				response.getWriter().write("Expired");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return;
//		}
//
//		// suspend the request
//		continuation.suspend(response); // response may be wrapped.
//		System.err.println("Suspended");
//		// register with async service. The code here will depend on the
//		// the service used (see Jetty HttpClient for example)
//		Timer t = new Timer();
//		t.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				try {
//					response.getWriter().write("Complete: "+new Date());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				continuation.complete();
//			}
//		}, 5000);
//	}
//
//	@Override
//	protected void service(final HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		final PrintWriter writer = resp.getWriter();
//		try {
//			final Continuation continuation = ContinuationSupport
//					.getContinuation(req);
//			continuation.suspend(resp); // response may be wrapped.
//			// register with async service. The code here will depend on the
//			// the service used (see Jetty HttpClient for example)
//			Timer t = new Timer();
//			t.schedule(new TimerTask() {
//				
//				@Override
//				public void run() {
//					try {
//						System.err.println("Exiting Servlet ...");
//						
//						ByteArrayOutputStream baos = new ByteArrayOutputStream();
//						CopyUtils.copy(getClass().getResourceAsStream("tmlexample.xml"), baos);
//						byte[] data = baos.toByteArray();
//						System.err.println("! data: "+data.length);
//						CopyUtils.copy(data, writer);
//						writer.flush();
//						writer.close();
//						System.err.println("post caught");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					continuation.complete();
//				}
//			}, 5000);
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}
//	
	@Override
	protected void service(final HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			TmlContinuationRunner tmlRunner = (TmlContinuationRunner) req
					.getAttribute("tmlRunner");
			if (tmlRunner != null) {
				// tmlRunner.endTransaction();
				return;
			}
			final LocalClient lc = getLocalClient(req);

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
			TmlContinuationRunner instantiateRunnable = new TmlContinuationRunner(request,lc);
			req.setAttribute("tmlRunner", instantiateRunnable);
			getTmlScheduler().submit(instantiateRunnable, false);
			instantiateRunnable.suspendContinuation();
		} catch (Throwable e) {
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
	public TmlScheduler getTmlScheduler() {
		if (tmlScheduler != null) {
			return tmlScheduler;
		}
		TmlScheduler attribute = (TmlScheduler) getServletContext()
				.getAttribute("tmlScheduler");
		return attribute;
	}



}
