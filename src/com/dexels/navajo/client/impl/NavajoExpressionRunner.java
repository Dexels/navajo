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

	public static void main(String[] args) throws Exception {

		//    	for (int i = 0; i < args.length; i++) {
		//			System.err.println("Arg # "+i+" "+args[i]);
		//		}
		System.setProperty("com.dexels.navajo.DocumentImplementation",
				"com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
		//      String script = args[1];
		//        String tmlFile = args[0];
		//			String config = "navajo-tester/auxilary/config";
		//			String tml = "navajo-tester/auxilary/tml";
		//			File confDir = new File(config);
		//			File tmlDir = new File(args[2]);
		//			File server = new File(confDir,"server.xml");

		ServerSocket server = new ServerSocket(PORT);

		//			System.err.println("User dir: "+System.getProperty("user.dir"));
		boolean running = true;

		while (running) {

			Socket conn = server.accept();

			InputStream in = conn.getInputStream();

			Navajo myNavajo = null;
			if (args.length > 0) {
				File tmlFile = new File(args[0]);
				if (tmlFile.exists()) {
					FileInputStream fis = new FileInputStream(tmlFile);
					myNavajo = NavajoFactory.getInstance().createNavajo(fis);
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn
					.getOutputStream()));

			String inp = br.readLine();
			//			    System.err.println("Input: "+inp);
			if (inp == null) {
				continue;
			}
			if ("exit".equals(inp)) {
				conn.close();
				server.close();
				return;
			}
			if (inp.startsWith("E|")) {
				processEvaluate(inp.substring(2), pw, myNavajo);
				continue;
			}
			if (inp.startsWith("R|")) {
				processRemarks(inp.substring(2), pw);
				continue;
			}
			if (inp.startsWith("T|")) {
				String inpp = inp.substring(2);
				pw.write("Test ok. Received: " + inpp);
				pw.flush();
				continue;
			}

			pw.write("unknown problem\n");
			pw.flush();
			//			    System.out.println(o.value);
		}

	}

	@SuppressWarnings("unchecked")
	private static void processRemarks(String func, PrintWriter pw) {
	
		//System.err.println("Processing: "+func);
		Class funcClass;
		try {
			funcClass = Class.forName(func);
			Object nw = funcClass.newInstance();
			if (nw instanceof FunctionInterface) {
				FunctionInterface nnn = (FunctionInterface) nw;
				//				String uu = nnn.usage();
				if (nnn.usage() == null || "".equals(nnn.usage())) {
					pw.write("No usage information.|");
				} else {
					pw.write(nnn.usage() + "|");
				}
				if (nnn.remarks() == null || "".equals(nnn.remarks())) {
					pw.write("No remarks.\n");
				} else {
					pw.write(nnn.remarks() + "\n");
				}
			} else {
				pw.write("Error:|Class is not a function?!\n");
			}
		} catch (ClassNotFoundException e) {
			//			e.printStackTrace();
			pw.write("Error:|Class: " + func + " could not be found\n");
		} catch (InstantiationException e) {
		
			pw.write("Error:|Class: " + func + " could not be instantiated\n");
			//			e.printStackTrace();
		} catch (IllegalAccessException e) {
		
			pw.write("Error:|Class: " + func + " could not be instantiated\n");
			//			e.printStackTrace();
		} finally {
			pw.flush();
		}
	}

	/**
	 * @param string
	 * @param pw
	 */
	private static void processEvaluate(String string, PrintWriter pw,
			Navajo nav) {
		Operand o;
		try {
			o = myEvaluator.evaluate(string, nav);
			pw.write("[type: " + o.type + "]\t   " + o.value + "\n");
			pw.flush();
		} catch (Throwable e) {
			//			e.printStackTrace();
			pw.write("ERROR: " + e.getMessage() + "\n");
			pw.flush();
		}

	}

}
