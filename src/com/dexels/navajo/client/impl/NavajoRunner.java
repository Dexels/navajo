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

    @SuppressWarnings({ "unchecked", "deprecation" })
	public static void main(String[] args) throws Exception {
//    	
//    	for (int i = 0; i < args.length; i++) {
//			System.err.println("Arg # "+i+" "+args[i]);
//		}
	   
			System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
			String script = args[1];

			File tmlDir = new File(args[2]);
			File server = new File(args[0]);
			
			String sourceTml = null;
			String sourceTmlName = null;
					
			String username = System.getProperty("navajo.user","ik");
			String password = System.getProperty("navajo.password","ik");
			String scriptClassName = script.replaceAll("/",".");
			Class scriptClass = Class.forName(scriptClassName,true,NavajoRunner.class.getClassLoader());
			if (scriptClass==null) {
				System.err.println("Class not found?!");
			}
			DirectClientImpl dci = new DirectClientImpl();
			dci.setUsername(username);
			dci.setPassword(password);
			dci.init(server.toURL(),NavajoRunner.class.getClassLoader(),System.getProperty("user.dir"));
		      Dispatcher.getInstance().getNavajoConfig().setClassloader(new NavajoBasicClassLoader(dci.getClass().getClassLoader()));

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
				reply.getHeader().setHeaderAttribute("sourceScript", sourceTmlName);
                
            }
			reply.getHeader().setHeaderAttribute("local","true");
			
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
