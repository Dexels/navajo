package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.WebContainerServlet;
import nextapp.echo2.webrender.WebRenderServlet;
import nextapp.echo2.webrender.service.SessionExpiredService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiServlet extends WebContainerServlet {

    private TipiEchoInstance tipiInstance = null;

	static {
        // echopoint.ui.Installer.register();
        // CustomUIComponents.register();

        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
        
    }

    public void destroy() {
    	System.err.println("IN SERVLET DESTROY!");
    	super.destroy();
    }

	public ApplicationInstance newApplicationInstance() {
      	tipiInstance = null;
        try {
         	tipiInstance = new TipiEchoInstance(getServletConfig(),getServletContext());
         	HttpSession hs = WebRenderServlet.getActiveConnection().getRequest().getSession();
         	hs.setAttribute("tipiInstance",tipiInstance);
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return tipiInstance;
    }

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String eval = request.getParameter("evaluate");
		if(eval==null) {
			super.process(request, response);
			return;
		}
		
		TipiEchoInstance instance = (TipiEchoInstance) request.getSession().getAttribute("tipiInstance");
		if(instance==null) {
			System.err.println("Whoops, no instance");
			response.getWriter().write("No instance");
		} else {
			Operand o = null;
			try {
				o = instance.getTipiContext().evaluate(eval, instance.getTipiContext().getDefaultTopLevel(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(o==null) {
				response.getWriter().write("Evaluated to null");
			} else {
				if(o.value instanceof Binary) {
					Binary b = (Binary)o.value;
					response.setContentType(b.guessContentType());
					response.setContentLength((int) b.getLength());
					ServletOutputStream outputStream = response.getOutputStream();
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
