package com.dexels.navajo.tipi.components.echoimpl;

import javax.servlet.http.HttpSessionListener;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.WebContainerServlet;
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
      	TipiEchoInstance tp = null;
        try {
         	tp = new TipiEchoInstance(getServletConfig());
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return tp;
    }

}
