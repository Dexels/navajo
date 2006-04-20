package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.WebContainerServlet;

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

        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

    }

    public void destroy() {
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
