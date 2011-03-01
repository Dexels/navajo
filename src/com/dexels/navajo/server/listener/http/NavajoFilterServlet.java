package com.dexels.navajo.server.listener.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.listeners.SchedulerRegistry;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.listener.http.standard.TmlStandardRunner;
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
//	private String inputFilterClass = null;
//	private String outputFilterClass = null;
//	private String postmanServletName = null;
//	private NavajoRequestWrapper inputFilter;
//	private NavajoResponseWrapper outputFilter;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		doProcess(req,resp);
	}
	
	protected void doProcess(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		
		boolean precheck = getTmlScheduler().preCheckRequest(req);
		if(!precheck) {
			req.getInputStream().close();
			resp.getOutputStream().close();
			return;
		}
//		System.err.println("Precheck complete");
//		Navajo inputDoc = NavajoFactory.getInstance().createNavajo(req.getInputStream());
//		req.getInputStream().close();

	  	  Object certObject = req.getAttribute( "javax.servlet.request.X509Certificate");
			String recvEncoding = req.getHeader("Content-Encoding");
			String sendEncoding = req.getHeader("Accept-Encoding");

			Navajo input = buildRequest(getInitParameter("inputFilterClass"), req);
			boolean check = getTmlScheduler().checkNavajo(input);

			TmlRunnable tr = new TmlStandardRunner(req,input, resp,  sendEncoding, recvEncoding, certObject) {

				@Override
				public void writeOutput(Navajo inDoc, Navajo outDoc)
						throws IOException, FileNotFoundException,
						UnsupportedEncodingException, NavajoException {
					System.err.println("WRITING  OUTPUT.......");
					processResponse(req, inDoc, outDoc, resp);
				}

				@Override
				public void run() {
					System.err.println(" Start run");
					super.run();
					System.err.println(" End run");
				}
				
				
			};

		if(!check) {
			resp.getOutputStream().close();
			return;
		}
		System.err.println("Check complete");
		
		System.err.println("Runnable created");
		SchedulerRegistry.getScheduler().run(tr);
		System.err.println("Post finished");

		
		
		
	
		
		

////		RequestDispatcher rd = req.getRequestDispatcher(postmanServlet);
//		if(outputFilter==null) {
//			// no output intervention required, we can forward:
//			rd.forward(_req, resp);
//		} else {
//			rd.include(_req, new NavajoServletResponseWrapper(resp));
//			System.err.println("Request: "+_req);
//			processResponse(req,request,nsrw, resp);
//		}
	}


	private Navajo buildRequest(String inFilter, HttpServletRequest request) throws ServletException, IOException {
		NavajoRequestWrapper nrw = getRequestWrapper(inFilter);
		if(nrw==null) {
			nrw = new IdentityRequestWrapper();
		}
		return nrw.processRequestFilter(request);
	}
	
	/**
	 * @param originalRequest
	 * @param wrappedRequest could be null, if no inputFilter has been supplied
	 * @param wrappedResponse could be null, if no outputFilter has been supplied
	 * @param originalResponse
	 * @throws IOException 
	 */
	private void processResponse(HttpServletRequest originalRequest, Navajo indoc, Navajo outdoc,HttpServletResponse originalResponse) throws IOException {
		NavajoResponseWrapper nrw = getResponseWrapper(getInitParameter("outputFilterClass"));
		if(nrw==null) {
			nrw = new IdentityResponseWrapper();
		}
		nrw.processResponse(originalRequest, indoc, outdoc, originalResponse);
	}


	
	
	@SuppressWarnings("unchecked")
	private NavajoRequestWrapper getRequestWrapper(String inFilter) {
		try {
			Class<? extends NavajoRequestWrapper> rwrapperClass = (Class<? extends NavajoRequestWrapper>) Class.forName(inFilter);
			return rwrapperClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private NavajoResponseWrapper getResponseWrapper(String outFilter) {
		try {
			Class<? extends NavajoResponseWrapper> rwrapperClass = (Class<? extends NavajoResponseWrapper>) Class.forName(outFilter);
			return rwrapperClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
