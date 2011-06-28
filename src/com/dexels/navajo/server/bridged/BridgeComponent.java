package com.dexels.navajo.server.bridged;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

import com.dexels.navajo.server.listener.NavajoContextListener;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import org.eclipse.equinox.jsp.jasper.JspServlet;

public class BridgeComponent {

	private static final String SERVLET_ALIAS = "/Postman";
	private HttpService httpService = null;
	private BundleContext bundleContext = null;

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public void setHttpService(HttpService httpService) {
		System.err.println("Injecting HTTP service");
		this.httpService = httpService;
	}

	public void startup() {
		try {
			HttpContext cc = httpService.createDefaultHttpContext();
//			HttpContext commonContext = new BundleEntryHttpContext(context.getBundle(), "/web"); //$NON-NLS-1$
			httpService.registerResources("/jsp-examples", "/", cc); //$NON-NLS-1$ //$NON-NLS-2$
			Servlet adaptedJspServlet = new JspServlet(bundleContext.getBundle(), "/web");  //$NON-NLS-1$//$NON-NLS-2$
			httpService.registerServlet("*.jsp", adaptedJspServlet, null, cc); //$NON-NLS-1$

			System.out.println("Staring up sevlet at " + SERVLET_ALIAS);
			TmlHttpServlet servlet = new TmlHttpServlet();
			httpService.registerServlet(SERVLET_ALIAS, servlet, null, null);
			NavajoContextListener.initializeContext(servlet.getServletContext(), null);
			System.err.println("Context initialized!");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {

	}

	public void modified() {

	}

}
