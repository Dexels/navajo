package com.dexels.navajo.tipi.vaadin.application.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

public class TipiVaadinServlet extends AbstractApplicationServlet {

	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinServlet.class);

	private static final long serialVersionUID = 8125011483209557703L;
	private ContextInstance contextInstance;
	private Set<Application> applications = new HashSet<Application>();
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
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

	public void deactivate() {
		logger.info("Deactivating Vaadin Servlet");
		for (Application  a: applications) {
			a.close();
		}
		applications.clear();
	}

	
	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return TipiVaadinApplication.class;
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
