/*
 * Created on Feb 28, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.client.impl;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.loader.NavajoBasicClassLoader;
import com.dexels.navajo.server.Dispatcher;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoRunner {

    /**
     * 
     */
    public NavajoRunner() {
        super();
    
    }

    public static void main(String[] args) throws Exception {
//    	
//    	for (int i = 0; i < args.length; i++) {
//			System.err.println("Arg # "+i+" "+args[i]);
//		}
	   
			System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
      String script = args[1];
			//        String tmlFile = args[0];
//			String config = "navajo-tester/auxilary/config";
//			String tml = "navajo-tester/auxilary/tml";
//			File confDir = new File(config);
			File tmlDir = new File(args[2]);
//			File server = new File(confDir,"server.xml");
			File server = new File(args[0]);
			
			String sourceTml = null;
			String sourceTmlName = null;
		
//			System.err.println("User dir: "+System.getProperty("user.dir"));
//			String cp = System.getProperty("java.class.path");
			
//			System.err.println(">>>>>\n"+cp.replaceAll(";","\n")+"\n>>>>>\n");

			
			String username = System.getProperty("navajo.user","ik");
			String password = System.getProperty("navajo.password","ik");
			String scriptClassName = script.replaceAll("/",".");
			Class scriptClass = Class.forName(scriptClassName,true,NavajoRunner.class.getClassLoader());
//			Class scriptClass = Class.forName("com.sybase.jdbc2.jdbc.SybDriver");
			if (scriptClass==null) {
				System.err.println("Class not found?!");
			}
			DirectClientImpl dci = new DirectClientImpl(true);
			dci.setUsername(username);
			dci.setPassword(password);
			dci.init(server.toURL(),NavajoRunner.class.getClassLoader(),System.getProperty("user.dir"));
		      Dispatcher.getInstance().getNavajoConfig().setClassloader(new NavajoBasicClassLoader(dci.getClass().getClassLoader()));

//			System.err.println("Classloader: "+NavajoRunner.class.getClassLoader());
			Navajo n = null;
			if (args.length>3) {
			    sourceTml = args[3];
			    sourceTmlName = args[4];
			    n = NavajoFactory.getInstance().createNavajo(new FileInputStream(args[3]));
			} else {
				n = NavajoFactory.getInstance().createNavajo();
			}
			
			
			Navajo reply = dci.doSimpleSend(n,script);

			if (sourceTml!=null) {
				reply.getHeader().setAttribute("sourceScript", sourceTmlName);
                
            }
			reply.getHeader().setAttribute("local","true");
			
//			Navajo reply = NavajoFactory.getInstance().createNavajo();
//			Access a = new Access(1,2,3,"Eclipse Developer","plug1n","3cl1ps3","localhost","localhost",false,cf);
//			a.setCompiledScript(sc);
//			a.setInDoc(n);
//			a.setOutputDoc(reply);
//			sc.run(new Parameters(),n,a,null);
//			
			
//			reply.write(System.err);
			//			
			if (!tmlDir.exists()) {
				tmlDir.mkdirs();
			}
			File f = new File(tmlDir,script+".tml");
			File parentDir = f.getParentFile();
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			System.err.println("About to write tml: "+f.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(f);
			reply.write(fos);
			fos.flush();
			fos.close();
//			Runtime.runFinalizersOnExit(true);
			System.err.println("Ending main thread...");
			System.exit(0);
		
    }
    
    public String usage() {
        return "NavajoRunner <tml-file>";
    }
}
