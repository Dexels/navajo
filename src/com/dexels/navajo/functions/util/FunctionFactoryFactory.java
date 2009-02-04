package com.dexels.navajo.functions.util;


import com.dexels.navajo.parser.FunctionInterface;

public class FunctionFactoryFactory {

	private static volatile FunctionFactoryInterface instance = null;
	private static Object semaphore = new Object();
	
	private FunctionFactoryFactory() {	
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
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < 10000; i++) {
			//Abs fi = new Abs(); 
			FunctionInterface fi = fii.getInstance(cl, "Abs");
			fi.reset();
			fi.insertOperand(new Integer(30));
			Object o = fi.evaluateWithTypeChecking();
		}
		System.err.println("With type checking, took: " +  ( System.currentTimeMillis() - start ) + " millis.");
//		
//		FunctionInterface fi = fii.getInstance(cl, "FormatDate");
//		fi.reset();
//		fi.insertOperand(new Float(-40.0));
//		try {
//		Object o = fi.evaluateWithTypeChecking();
//		System.err.println(o);
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		
//		FunctionInterface fi2 = fii.getInstance(cl, "Abs");
//		fi2.reset();
//		fi2.insertOperand(new String("aap"));
//		try {
//		Object o2 = fi2.evaluateWithTypeChecking();
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		FunctionInterface fi = fii.getInstance(cl, "FormatStringList");
//		fi.reset();
//		ArrayList aap = new ArrayList();aap.add("noot");aap.add("mies");aap.add("vuur");
//		fi.insertOperand(aap);
//		//fi.insertOperand("*");
//		//fi.insertOperand(new Binary(new FileInputStream("/home/arjen/@")));
//		//fi.insertOperand(new SimpleDateFormat("dd-mm-yyyy").parse("18-10-1999"));
//		Object o = fi.evaluateWithTypeChecking();
//		System.err.println(o);
		
	}
}
