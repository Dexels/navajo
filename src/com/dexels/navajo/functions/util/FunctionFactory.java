package com.dexels.navajo.functions.util;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.server.DispatcherFactory;

public class FunctionFactory extends FunctionFactoryInterface {

	private static volatile FunctionFactoryInterface instance = null;
	private static Object semaphore = new Object();
	
	private FunctionFactory() {	
	}
	
	public void init() {
		// Read functiondef configuration file.
		String path = DispatcherFactory.getInstance().getNavajoConfig().getConfigPath();
		System.err.println("path = " + path);
	}
	
	public static FunctionFactoryInterface getInstance() {
		FunctionFactoryInterface fii;
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized (semaphore) {
			
			if ( instance != null ) {
				return instance;
			}
			
			if ( System.getProperty("NavajoFunctionFactory") != null ) {
				try {
					Class c = Class.forName(System.getProperty("NavajoFunctionFactory"));
					instance = (FunctionFactoryInterface) c.newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				
			} else {
				instance = new FunctionFactory();
			}
			instance.init();
		}
		
		return instance;
		
	}
	
	
	public FunctionInterface getInstance(ClassLoader cl, String functionName) {
		return null;
	}
	
	public static void main(String [] args) throws Exception {
		
		System.setProperty("NavajoFunctionFactory", "com.dexels.navajo.functions.util.TestFunctionFactory");
		FunctionFactoryInterface fii = FunctionFactory.getInstance();
		FunctionInterface fi = fii.getInstance(Thread.currentThread().getContextClassLoader(), "Abs");
		System.err.println(fi.usage());
		Thread.sleep(5000);
		
		fi = fii.getInstance(Thread.currentThread().getContextClassLoader(), "Abs2");
		System.err.println(fi.usage());
	}
}
