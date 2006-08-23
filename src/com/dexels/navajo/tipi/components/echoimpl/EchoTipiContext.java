package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.*;
import nextapp.echo2.webrender.*;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiContext;

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

public class EchoTipiContext extends TipiContext {
    private ApplicationInstance myServerContext;

    public EchoTipiContext() {
    }

    public Set getRequiredIncludes() {
        Set s = super.getRequiredIncludes();
        s.add("com/dexels/navajo/tipi/components/echoimpl/echoclassdef.xml");
        // s.add("com/dexels/navajo/tipi/actions/echoactiondef.xml");
        return s;
    }

    public void setSplashInfo(String s) {
        /**
         * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
         *       method
         */
    }

    public void setSplashVisible(boolean b) {
        /**
         * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
         *       method
         */
    }

    public void setSplash(Object s) {
        /**
         * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
         *       method
         */
    }

    public void clearTopScreen() {

    }

    public int getPoolSize() {
        return 0;
    }

    public void setServerContext(ApplicationInstance sc) {
        myServerContext = sc;
    }
    public ApplicationInstance getServerContext() {
        return myServerContext;
    }

    public void exit() {
    	System.err.println("CALLED EXIT IN TIPIECHO CONTEXT. IGNORING");
    	NavajoClientFactory.getClient().destroy();
    }
    
    public void finalize() {
    	System.err.println("EchoTipiContext has been finalized!!!1");
    }
    
    public void exit(String destination) {
        ((TipiEchoInstance) myServerContext).exitToUrl(destination);
    }

    public URL getDynamicResourceBaseUrl(String path) throws MalformedURLException {
        Connection con = WebRenderServlet.getActiveConnection();
        HttpServletRequest req = con.getRequest();
        String url = req.getRequestURL().toString();
        URL u = new URL(url);
        System.err.println("URL:: "+u);
//        System.err.println("Context: "+getServletContext().get());
        for (Enumeration iter = getServletContext().getAttributeNames(); iter.hasMoreElements();) {
            String element = (String) iter.nextElement();
            System.err.println("Attribute: "+element);
        }
        URL rootURL =  new URL(u.getProtocol(),u.getHost(),u.getPort(),path);
//        System.err.println("Assembled rootURL: "+rootURL.toString());
//        URL u2 = new URL(rootURL,"dynamic");
//        System.err.println("Returing: "+u2.toString());
        return rootURL;
        
    }

    public File getDynamicResourceBaseDir() {
        File ff = new File(getServletContext().getRealPath("/dynamic"));
        System.err.println("My context name: "+getServletContext().getServletContextName());
        System.err.println("USING DIR: "+ff);
        if (!ff.exists()) {
            ff.mkdirs();
        }
        return ff;
    }

    public void debugLog(String category, String event) {
     
        long stamp = System.currentTimeMillis()-startTime;
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("HH:mm:ss S");

//        Calendar c = Calendar.getInstance();
        Date d = new Date(stamp);
//        c.setTimeInMillis(stamp);
        
//        try {
//          FileWriter fw = new FileWriter(System.getProperty("user.home") + "/.tipidebug", true);
          System.err.println(category + ", " + inputFormat1.format(d) + ", " + " Thread: " + Thread.currentThread().getName() + "," + event+"\n");
//          fw.flush();
//          fw.close();
//        }
//        catch (IOException ex) {
//          ex.printStackTrace();
//        }
      }
    public ServletContext getServletContext() {
 
        ApplicationInstance app = ApplicationInstance.getActive();
        ContainerContext containerContext  
           = (ContainerContext) app.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
        ServletContext sc = containerContext.getSession().getServletContext();
        return sc;
    }

}
