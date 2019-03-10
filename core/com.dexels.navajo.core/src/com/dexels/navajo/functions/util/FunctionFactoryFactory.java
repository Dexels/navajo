package com.dexels.navajo.functions.util;


import java.security.AccessControlException;

import navajocore.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;


public class FunctionFactoryFactory {

	private static  FunctionFactoryInterface instance = null;
	private static Object semaphore = new Object();
	
	private static final Logger logger = LoggerFactory
			.getLogger(FunctionFactoryFactory.class);
	
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
					instance = c.getDeclaredConstructor().newInstance();
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
		fi.insertIntegerOperand(Integer.valueOf(21210));
		fi.insertStringOperand("SELECT * FROM AAP WHERE noot = ?");
		fi.insertStringOperand("PIPO");
		fi.evaluateWithTypeChecking();
		
	}
}
