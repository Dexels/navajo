package com.dexels.navajo.functions.util;


import java.security.AccessControlException;

import navajocore.Version;

import com.dexels.navajo.parser.FunctionInterface;


public class FunctionFactoryFactory {

	private static volatile FunctionFactoryInterface instance = null;
	private static Object semaphore = new Object();
	
	private FunctionFactoryFactory() {	
	}
	

		
	@SuppressWarnings("unchecked")
	public static FunctionFactoryInterface getInstance() {
//		FunctionFactoryInterface fii;
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized (semaphore) {
			
			if ( instance != null ) {
				return instance;
			}
			
			String func = null;
			
			try {
				func = System.getProperty("NavajoFunctionFactory");
			} catch (AccessControlException e1) {
				// can't read property. Whatever, func remains null.
			}

			try {
				if(Version.getDefaultBundleContext()!=null) {
					System.err.println("OSGi environment detected!");
					func = "com.dexels.navajo.functions.util.OsgiFunctionFactory";
				}
			} catch (Throwable t) {
				System.err.println("NO OSGi environment detected!");
			}
			
			if ( func != null ) {
				try {
					Class<? extends FunctionFactoryInterface> c = (Class<? extends FunctionFactoryInterface>) Class.forName(func);
					instance = (FunctionFactoryInterface) c.newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			} else {
				instance = new JarFunctionFactory();
			}
			instance.init();
		}
		
		return instance;
		
	}
	
	public static void main(String [] args) throws Exception {
		
		//System.setProperty("NavajoFunctionFactory", "com.dexels.navajo.functions.util.JarFunctionFactory");
		FunctionFactoryInterface fii = FunctionFactoryFactory.getInstance();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
	
		FunctionInterface fi = fii.getInstance(cl, "SingleValueQuery");
		fi.reset();
		fi.insertOperand(new Integer(21210));
		fi.insertOperand("SELECT * FROM AAP WHERE noot = ?");
		fi.insertOperand("PIPO");
		fi.evaluateWithTypeChecking();
		
	}
}
