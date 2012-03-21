package com.dexels.navajo.tipi.vaadin.application.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

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
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.ApplicationServlet;

public class TipiVaadinServlet extends ApplicationServlet {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 8125011483209557703L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	

	@Override
	protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
		return TipiVaadinApplication.class;
	}



	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
//		String appInstance = getInitParameter("application");
//		Class<? extends Application> appInstanceClass = Class.forName(appInstance);
		TipiVaadinApplication tipiApplication = (TipiVaadinApplication) super.getNewApplication(request);
		
		tipiApplication.setLocale(new Locale("nl","NL"));
		tipiApplication.setServletContext(getServletContext());
		String referer = request.getHeader("x-forwarded-host");
		System.err.println("Creating application. Referer: "+referer);
		tipiApplication.setReferer(referer);

     	HttpSession hs = request.getSession();
     	hs.setAttribute("tipiInstance",tipiApplication);
		// add request data?
		return tipiApplication;
	}

//    protected void service(HttpServletRequest request,
//            HttpServletResponse response) throws ServletException, IOException {
//    			String eval = request.getParameter("evaluate");
//    		System.err.println("wEVAAAAAAL: "+eval);
//    }
//	
	@Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		String eval = request.getParameter("evaluate");
    	Enumeration<String> s = request.getHeaderNames();
    	while (s.hasMoreElements()) {
			String name = (String) s.nextElement();
			System.err.println("Header: "+name+" value: "+request.getHeader(name));
		}
    	
		if(eval==null) {
			super.service(request, response);
			return;
		}
		
		TipiVaadinApplication instance = (TipiVaadinApplication) request.getSession().getAttribute("tipiInstance");
		if(instance==null) {
			System.err.println("Whoops, no instance");
			response.getWriter().write("No instance");
		} else {
			
			Operand o = null;
			try {
				o = instance.getCurrentContext().evaluate(eval, instance.getCurrentContext().getDefaultTopLevel(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(o==null) {
				response.getWriter().write("Evaluated to null");
			} else {
				logger.info("Serving: "+o.value);
				if(o.value instanceof Binary) {
					Binary b = (Binary)o.value;
					String contentType = b.guessContentType();
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
	


	
}
