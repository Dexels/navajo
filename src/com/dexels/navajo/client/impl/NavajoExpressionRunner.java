/*
 * Created on Apr 15, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.client.impl;

import java.io.*;
import java.net.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoExpressionRunner {
    
    public final static int PORT = 3001;
    
    private static ExpressionEvaluator myEvaluator = new DefaultExpressionEvaluator();
    
    public static void main(String[] args) throws Exception{
    	
    	for (int i = 0; i < args.length; i++) {
			System.err.println("Arg # "+i+" "+args[i]);
		}
			System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
//      String script = args[1];
			//        String tmlFile = args[0];
//			String config = "navajo-tester/auxilary/config";
//			String tml = "navajo-tester/auxilary/tml";
//			File confDir = new File(config);
//			File tmlDir = new File(args[2]);
//			File server = new File(confDir,"server.xml");

			ServerSocket server = new ServerSocket(PORT);
			
			Socket conn = server.accept();
			
			InputStream in = conn.getInputStream();
			
            Navajo myNavajo  = null;
            if (args.length>0 ) {
             	File tmlFile = new File(args[0]);
             	if (tmlFile.exists()) {
        			FileInputStream fis = new FileInputStream(tmlFile);
           			myNavajo = NavajoFactory.getInstance().createNavajo(fis);
           		 			}
            }
			
			System.err.println("User dir: "+System.getProperty("user.dir"));
			
			boolean running = true;

		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
		    
			while (running) {
			    String inp = br.readLine();
			    if ("exit".equals(inp)) {
			        conn.close();
			        server.close();
                    return;
                }
			    Operand o;
				try {
					o = myEvaluator.evaluate(inp, myNavajo);
				    pw.write("[type: "+o.type+"] "+o.value+"\n");
				    pw.flush();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				    pw.write("ERROR: "+e.getMessage()+"\n");
				    pw.flush();
				}
				//			    System.out.println(o.value);
			}
				

    }
    

}
