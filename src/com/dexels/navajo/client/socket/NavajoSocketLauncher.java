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

    public static void main(String[] args) throws Exception {
//        System.err.println("# of args: "+args.length);
           int port = 9999;
        if (args.length>0) {
            port = Integer.parseInt(args[0]);
        }
        String configUrl = null; 
            //"file:///c:/toy-workspace/sportlink-serv/navajo-tester/auxilary/config/server_simple.xml";
        if (args.length>1) {
            configUrl = args[1];
        }
        
        URL config = null;
        if (configUrl==null) {
            File f = new File("navajo-tester/auxilary/config/server.xml");
            if (f.exists()) {
                config = f.toURL();
            } else {
                System.err.println("Can't find server.xml in default location, and none is specified");
            }
        } else {
            config = new URL(configUrl);
        }
        
        System.setProperty("com.dexels.navajo.DocumentImplementation",
        "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

        final String dir = "c:/toy-workspace/sportlink-serv/navajo-tester";
        DirectClientImpl dci = new DirectClientImpl();
        dci.init(config,dir);
        NavajoSocketListener nsl = new NavajoSocketListener(dci,port);
//        System.err.println("Main terminating.");
    }
}


