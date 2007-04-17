package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Window;
import nextapp.echo2.webcontainer.*;
import nextapp.echo2.webrender.*;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
//    private ApplicationInstance myServerContext;
    private static int instanceCount = 0;
    private final TipiEchoInstance myInstance;
    public EchoTipiContext(TipiEchoInstance t) {
    	instanceCount++;
    	myInstance = t;

    }

    public ApplicationInstance getInstance() {
    	return myInstance;
    }

    public Set getRequiredIncludes() {
        Set s = super.getRequiredIncludes();
        s.add("com/dexels/navajo/tipi/components/echoimpl/echoclassdef.xml");
        // s.add("com/dexels/navajo/tipi/actions/echoactiondef.xml");
        return s;
    }
    
    public void finalize() {
    	instanceCount--;
    	System.err.println("EchoTipiContext has been finalized!!! Instancecount now: "+instanceCount);
    	 Window w = (Window) getTopLevel();
    	 w.dispose();
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

//    public void setServerContext(ApplicationInstance sc) {
//        myServerContext = sc;
//    }
//    public ApplicationInstance getServerContext() {
//        return myServerContext;
//    }

    public void exit() {
     	NavajoClientFactory.getClient().destroy();
    }
    

    public void exit(String destination) {
        ApplicationInstance ai = ApplicationInstance.getActive();
        if (ai instanceof TipiEchoInstance) {
            ((TipiEchoInstance)ai).exitToUrl(destination);
		}
    }

    public URL getDynamicResourceBaseUrl(String path) throws MalformedURLException {
        Connection con = WebRenderServlet.getActiveConnection();
        HttpServletRequest req = con.getRequest();
        String url = req.getRequestURL().toString();
        URL u = new URL(url);

        for (Enumeration iter = getServletContext().getAttributeNames(); iter.hasMoreElements();) {
            String element = (String) iter.nextElement();
        }
        URL rootURL =  new URL(u.getProtocol(),u.getHost(),u.getPort(),path);
        return rootURL;
        
    }

    public File getDynamicResourceBaseDir() {
        File ff = new File(getServletContext().getRealPath("/dynamic"));

        if (!ff.exists()) {
            ff.mkdirs();
        }
        return ff;
    }

    public void debugLog(String category, String event) {
     
        long stamp = System.currentTimeMillis()-startTime;
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("HH:mm:ss S");

        Date d = new Date(stamp);

          System.err.println(category + ", " + inputFormat1.format(d) + ", "  + "," + event+"\n");
      }
    public ServletContext getServletContext() {
 
        ApplicationInstance app = ApplicationInstance.getActive();
        ContainerContext containerContext  
           = (ContainerContext) app.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
        ServletContext sc = containerContext.getSession().getServletContext();
        return sc;
    }
    
    private static Map staticComponentMap = new HashMap();
    
   
    protected XMLElement getTipiDefinition(String name) throws TipiException {
      	XMLElement xe = (XMLElement) staticComponentMap.get(name);
      	if (xe==null) {
      		return super.getTipiDefinition(name);
		} else {

			return xe;
		}
    }
    
    protected void addComponentDefinition(XMLElement elm) {
        String defname = (String) elm.getAttribute("name");
        setSplashInfo("Loading statically: " + defname);
        staticComponentMap.put(defname, elm);
//        tipiMap.put(defname, elm);
      }


}
