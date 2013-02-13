package com.dexels.navajo.tipi.vaadin.touch.servlet;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.touch.application.TipiVaadinTouchApplication;
import com.vaadin.Application;
import com.vaadin.addon.touchkit.server.TouchKitApplicationServlet;

public class TipiVaadinTouchServlet extends TouchKitApplicationServlet {

	private static final long serialVersionUID = 8125011483209557703L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinTouchServlet.class);
	
	private ContextInstance contextInstance;
	private Set<Application> applications = new HashSet<Application>();

	
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	
	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		boolean supported = isSupportedBrowser(request);
		System.err.println("Supported: "+supported);

		//		Class<? extends Application> appInstanceClass = Class.forName(appInstance);
		TipiVaadinTouchApplication tipiApplication = (TipiVaadinTouchApplication) super.getNewApplication(request);
		tipiApplication.setServletContext(getServletContext());

		tipiApplication.init();
		tipiApplication.setServlet(this);
		applicationStarted(tipiApplication);
		if(contextInstance!=null) {
			logger.info("injected instance found");
			tipiApplication.setContextInstance(contextInstance);
		}
		tipiApplication.setLocale(new Locale("nl","NL"));
		tipiApplication.setServletContext(getServletContext());
		String referer = request.getHeader("x-forwarded-host");
		logger.info("Creating application. Referer: "+referer);
		tipiApplication.setReferer(referer);
		if(referer!=null) {
			tipiApplication.setLogoutURL("http://"+referer+request.getContextPath());
		}
     	HttpSession hs = request.getSession();
     	hs.setAttribute("tipiInstance",tipiApplication);
		// add request data?
		return tipiApplication;
	}

//	protected Application getNewApplication(HttpServletRequest request)
//			throws ServletException {
//		boolean supported = isSupportedBrowser(request);
//		System.err.println("Supported: "+supported);
////		Class<? extends Application> appInstanceClass = Class.forName(appInstance);
//		TipiVaadinTouchApplication tipiApplication = (TipiVaadinTouchApplication) super.getNewApplication(request);
//		tipiApplication.setServletContext(getServletContext());
//     	HttpSession hs = request.getSession();
//     	hs.setAttribute("tipiInstance",tipiApplication);
//		// add request data?
//		return tipiApplication;
//	}



	@Override
	protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
		return TipiVaadinTouchApplication.class;
	}



	public void setContextInstance(ContextInstance ci) {
		this.contextInstance = ci;
	}
	
	public void clearContextInstance(ContextInstance ci) {
		this.contextInstance = null;
	}

	private void applicationStarted(Application a) {
		applications.add(a);
	}

	public void applicationClosed(Application a) {
		applications.remove(a);
	}
}
