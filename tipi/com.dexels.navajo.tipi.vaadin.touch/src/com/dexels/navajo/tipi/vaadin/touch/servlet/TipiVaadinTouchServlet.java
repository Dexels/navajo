package com.dexels.navajo.tipi.vaadin.touch.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.instance.LocalTipiConnector;
import com.dexels.navajo.tipi.vaadin.touch.application.TipiVaadinTouchApplication;
import com.vaadin.Application;
import com.vaadin.addon.touchkit.server.TouchKitApplicationServlet;
import com.vaadin.terminal.gwt.server.Constants;

public class TipiVaadinTouchServlet extends TouchKitApplicationServlet {

	private static final long serialVersionUID = 8125011483209557703L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinTouchServlet.class);
	
	private ContextInstance contextInstance;
	private Set<Application> applications = new HashSet<Application>();
	private final Set<TipiContextListener> tipiContextListeners= new HashSet<TipiContextListener>();

	private LocalClient localClient;

	private String language =null;
	private String region = null;

	public void activate(final Map<String,Object> settings, BundleContext bundleContext) {
		logger.info("Activating Tipi Instance: {}",settings);
		final String profile = (String) settings.get("tipi.instance.profile");
		
		final String deployment= (String) settings.get("tipi.instance.deployment");
		language = (String) settings.get("tipi.instance.language");
		region = (String) settings.get("tipi.instance.region");

		ContextInstance ci = new ContextInstance() {
			
			@Override
			public String getProfile() {
				return profile;
			}
			
			@Override
			public String getPath() {
				return (String) settings.get("tipi.instance.path");
			}
			
			@Override
			public String getDeployment() {
				return deployment;
			}
			@Override
			public String getContext() {
				return null;
			}
		};
		setContextInstance(ci);
	}
	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		ServletConfig wrap = new ServletConfig(){

			@Override
			public String getServletName() {
				return servletConfig.getServletName();
			}

			@Override
			public ServletContext getServletContext() {
				return servletConfig.getServletContext();
			}

			@Override
			public String getInitParameter(String name) {
				if("application".equals(name)) {
					return TipiVaadinTouchApplication.class.getName();
				}
				if(Constants.PARAMETER_WIDGETSET.equals(name)) {
					return "com.dexels.navajo.tipi.vaadin.touch.widgetset.TipiWidgetset";
				}
				return servletConfig.getInitParameter(name);
			}

			@Override
			public Enumeration<String> getInitParameterNames() {
				final Enumeration<String> en = servletConfig.getInitParameterNames();
				List<String> l = new ArrayList<String>();
				while (en.hasMoreElements()) {
					String name = en.nextElement();
					l.add(name);
				}
				l.add("application");
				l.add(Constants.PARAMETER_WIDGETSET);
				Enumeration<String> ext = Collections.enumeration(l);
				return ext;
			}};
		super.init(wrap);
	}

	
	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		boolean supported = isSupportedBrowser(request);

		//		Class<? extends Application> appInstanceClass = Class.forName(appInstance);
		TipiVaadinTouchApplication tipiApplication = (TipiVaadinTouchApplication) super.getNewApplication(request);
		tipiApplication.setServletContext(getServletContext());
		for (TipiContextListener tc : tipiContextListeners) {
			tipiApplication.addTipiContextListener(tc);
		}
		if(localClient!=null) {
			tipiApplication.setDefaultConnector(new LocalTipiConnector(localClient));
		}
		tipiApplication.init();
		tipiApplication.setServlet(this);
		applicationStarted(tipiApplication);
		if(contextInstance!=null) {
			logger.info("injected instance found");
			tipiApplication.setContextInstance(contextInstance);
		}
		if(language!=null && region!=null) {
			tipiApplication.setLocale(new Locale(language,region));
			tipiApplication.setLocaleCode(language);
			tipiApplication.setSubLocaleCode(region);
			
		}
//		tipiApplication.setLocale(new Locale("nl","NL"));
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


	@Override
	protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
		return TipiVaadinTouchApplication.class;
	}

	public void deactivate() {
		region = null;
		language = null;
		for (Application a : applications) {
			a.close();
		}
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


	public void addTipiContextListener(TipiContextListener t) {
		tipiContextListeners.add(t);
	}

	public void removeTipiContextListener(TipiContextListener t) {
		tipiContextListeners.remove(t);
	}
	
	public void setLocalClient(LocalClient lc) {
		this.localClient = lc;
	}

	public void clearLocalClient(LocalClient lc) {
		this.localClient = null;
	}

}
