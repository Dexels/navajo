/*
 * Created on Feb 28, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.client.impl;

import java.io.*;
import java.net.MalformedURLException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.ClassloaderInputStreamReader;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;

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
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
    	
    	for (int i = 0; i < args.length; i++) {
			System.err.println("Arg # "+i+" "+args[i]);
		}
	    try {
	    	System.err.println("Aap: "+System.getProperty("aap"));
			System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
      String script = args[1];
			//        String tmlFile = args[0];
//			String config = "navajo-tester/auxilary/config";
//			String tml = "navajo-tester/auxilary/tml";
//			File confDir = new File(config);
			File tmlDir = new File(args[2]);
//			File server = new File(confDir,"server.xml");
			File server = new File(args[0]);
			
			
			
			
			
			NavajoConfig cf = new NavajoConfig(new FileInputStream(server),new ClassloaderInputStreamReader());
			
//			String scriptClassName = script.replaceAll("/",".");
//			CompiledScript sc = (CompiledScript)(scriptClass.newInstance());
//			NavajoClassLoader ncl = new NavajoClassLoader("aap","noot");
//			sc.setClassLoader(ncl);
//
			String cp = System.getProperty("java.class.path");
			
			System.err.println(">>>>>\n"+cp+"\n>>>>>\n");

			String scriptClassName = script.replaceAll("/",".");
			Class scriptClass = Class.forName(scriptClassName,true,NavajoRunner.class.getClassLoader());
			if (scriptClass==null) {
				System.err.println("Class not found?!");
			}
			System.err.println("Serverfile: "+server.getAbsolutePath());
			DirectClientImpl dci = new DirectClientImpl(true);
			dci.init(server.toURL());

			Navajo n = null;
			if (args.length>3) {
				n = NavajoFactory.getInstance().createNavajo(new FileInputStream(args[3]));
			} else {
				n = NavajoFactory.getInstance().createNavajo();
			}
			
			
			Navajo reply = dci.doSimpleSend(n,script);

			
//			Navajo reply = NavajoFactory.getInstance().createNavajo();
//			Access a = new Access(1,2,3,"Eclipse Developer","plug1n","3cl1ps3","localhost","localhost",false,cf);
//			a.setCompiledScript(sc);
//			a.setInDoc(n);
//			a.setOutputDoc(reply);
//			sc.run(new Parameters(),n,a,null);
//			
			reply.write(System.err);
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
		} catch (Exception e) {
				e.printStackTrace();
		} 
    }
    
    public String usage() {
        return "NavajoRunner <tml-file>";
    }
}
