package com.dexels.navajo.tipi.vaadin.application.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.ApplicationServlet;

public class TipiVaadinServlet extends ApplicationServlet {

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
		TipiVaadinApplication tipiApplication = (TipiVaadinApplication) super.getNewApplication(request);
		tipiApplication.setServletContext(getServletContext());

     	HttpSession hs = request.getSession();
     	hs.setAttribute("tipiInstance",tipiApplication);
		// add request data?
		return tipiApplication;
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String eval = request.getParameter("evaluate");
		if(eval==null) {
			super.doGet(request, response);
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
