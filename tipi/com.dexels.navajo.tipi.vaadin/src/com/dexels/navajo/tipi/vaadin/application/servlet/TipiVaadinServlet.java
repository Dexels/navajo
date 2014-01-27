package com.dexels.navajo.tipi.vaadin.application.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.dexels.navajo.tipi.vaadin.instance.LocalTipiConnector;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;
import com.vaadin.terminal.gwt.server.Constants;

public class TipiVaadinServlet extends AbstractApplicationServlet {

	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinServlet.class);

	private static final long serialVersionUID = 8125011483209557703L;
	private ContextInstance contextInstance;
	private Set<Application> applications = new HashSet<Application>();
	
	private final Set<TipiContextListener> tipiContextListeners= new HashSet<TipiContextListener>();

	private LocalClient localClient;

	private String language =null;
	private String region = null;

	protected String widgetset;
	protected String productionMode;

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
					return TipiVaadinServlet.class.getName();
				}
				if("productionMode".equals(name)) {
					return ""+TipiVaadinServlet.this.productionMode;
				}
				if(Constants.PARAMETER_WIDGETSET.equals(name)) {
					return ""+TipiVaadinServlet.this.widgetset;
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
				l.add("productionMode");
				Enumeration<String> ext = Collections.enumeration(l);
				return ext;
			}};
		super.init(wrap);
	}



	public void activate(final Map<String,Object> settings, BundleContext bundleContext) {
		logger.info("Activating Tipi Instance: {}",settings);
		final String profile = (String) settings.get("tipi.instance.profile");
		final String deployment= (String) settings.get("tipi.instance.deployment");
		language = (String) settings.get("tipi.instance.language");
		region = (String) settings.get("tipi.instance.region");
		productionMode = (String) settings.get("tipi.instance.productionmode");
		widgetset = (String) settings.get("tipi.instance.widgetset");

		
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
	protected void writeAjaxPageHtmlHeader(BufferedWriter page, String title,
			String themeUri, HttpServletRequest request) throws IOException {
		super.writeAjaxPageHtmlHeader(page, title, themeUri, request);
	       page.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\" />");
	}



	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
//		String appInstance = getInitParameter("application");
//		Class<? extends Application> appInstanceClass = Class.forName(appInstance);
		TipiVaadinApplication tipiApplication = new TipiVaadinApplication();
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
		tipiApplication.setServletContext(getServletContext());
		for (TipiContextListener tc : tipiContextListeners) {
			tipiApplication.addTipiContextListener(tc);
		}
		if(localClient!=null) {
			tipiApplication.setDefaultConnector(new LocalTipiConnector(localClient));
		}		
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
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		String eval = request.getParameter("evaluate");
		if(eval==null) {
			super.service(request, response);
			return;
		}
		
		TipiVaadinApplication instance = (TipiVaadinApplication) request.getSession().getAttribute("tipiInstance");
		if(instance==null) {
			response.getWriter().write("No instance");
		} else {
			
			Operand o = null;
			try {
				o = instance.getCurrentContext().evaluate(eval, instance.getCurrentContext().getDefaultTopLevel(), null);
			} catch (Exception e) {
				logger.error("Error: ",e);
			}
			if(o==null) {
				response.getWriter().write("Evaluated to null");
			} else {
				logger.info("Serving: "+o.value);
				if(o.value instanceof Binary) {
					Binary b = (Binary)o.value;
					
					String contentType = b.getMimeType();
					String explicitMime = request.getParameter("mime");
					if (explicitMime!=null) {
						contentType = explicitMime;
					}
					logger.info("Mime: "+contentType+" : "+b.getSubType("mailMime")+" exp: "+explicitMime);
					if(contentType==null) {
						b.guessContentType();
					}
					response.setContentType(contentType);
					response.setContentLength((int) b.getLength());
					ServletOutputStream outputStream = response.getOutputStream();
					if(contentType!=null && contentType.indexOf("html")==-1)	{
						response.setHeader("Content-Disposition", "attachment; filename=file."+b.getExtension());						
					}
					b.write(outputStream);
					outputStream.flush();
					return;
				}
				response.getWriter().write("Evaluated to: "+o.getClass()+"::::\n");
				
				response.getWriter().write("O: "+o.value);
						
			}
		}
		
	}

	public void activate() {
		logger.info("Activating Vaadin Servlet");
	}


	
	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return TipiVaadinApplication.class;
	}
	
	public void deactivate() {
		region = null;
		language = null;
		for (Application a : applications) {
			a.close();
		}
		applications.clear();
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
