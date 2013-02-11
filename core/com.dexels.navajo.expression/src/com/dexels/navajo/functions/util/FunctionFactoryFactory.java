package com.dexels.navajo.functions.util;


import java.security.AccessControlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import navajoexpression.Version;

import com.dexels.navajo.parser.FunctionInterface;


public class FunctionFactoryFactory {

	private static volatile FunctionFactoryInterface instance = null;
	private static Object semaphore = new Object();
	
	private final static Logger logger = LoggerFactory
			.getLogger(FunctionFactoryFactory.class);
	
	private static ClassLoader legacyClassLoader;
	
	
	public static ClassLoader getLegacyClassLoader() {
		return legacyClassLoader;
	}



	public static void setLegacyClassLoader(ClassLoader legacyClassLoader) {
		FunctionFactoryFactory.legacyClassLoader = legacyClassLoader;
	}



	private FunctionFactoryFactory() {	
	}
	
	
		
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
				if(Version.osgiActive()) {
					logger.debug("OSGi environment detected!");
					func = "com.dexels.navajo.functions.util.OsgiFunctionFactory";
				} else {
					logger.debug("no OSGi environment detected!");

				}
			} catch (Throwable t) {
				logger.debug("NO OSGi environment detection failed!");
			}
			
			if ( func != null ) {
				try {
					Class<? extends FunctionFactoryInterface> c = (Class<? extends FunctionFactoryInterface>) Class.forName(func);
					instance = c.newInstance();
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
