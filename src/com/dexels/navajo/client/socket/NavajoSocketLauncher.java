/*
 * Created on Jul 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.client.socket;

import java.io.*;
import java.net.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.client.impl.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.loader.*;
import com.dexels.navajo.server.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoSocketLauncher {
    public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
    public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
    public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
    public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

    public static void main(String[] args) throws Exception {
           int port = 10000;
           URL config = null;
      
           String configUrl = null; 
//    	for (int i = 0; i < args.length; i++) {
//    		System.err.println("Arg # "+i+" "+args[i]);
//    	}
           System.err.println("Running navajo for project: "+System.getProperty("navajo.project"));
        if (args.length>1) {
            File f = new File(args[1]);
            config = f.toURL();
        }
        if (args.length>0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                 e.printStackTrace();
                 System.err.println("Bad port number: "+args[0]+" using default");
            }
        }
         if (config==null) {
            File f = new File("navajo-tester/auxilary/config/server.xml");
            config = f.toURL();
                if (f.getAbsoluteFile().exists()) {
             } else {
            	System.err.println("f: "+f.getAbsolutePath());
                System.err.println("Can't find server.xml in default location, and none is specified");
            }
        }         

        System.setProperty(DOC_IMPL,QDSAX);

        
        
        
//        final String dir = "c:/toy-workspace/sportlink-serv/navajo-tester";
        String dir = System.getProperty("user.dir");
      DirectClientImpl dci = new DirectClientImpl();
      // Will use a BasicClassLoader:
//      dci.init(config,null,dir);
        // Will use a MultiClassLoader:
        dci.init(config,dir);

        // ---------------------
        dci.setUseAuthorization(false);
     //
        
        NavajoSocketListener nsl = new NavajoSocketListener(dci,port,dir, config);
//        System.err.println("Main terminating.");
    }
}


