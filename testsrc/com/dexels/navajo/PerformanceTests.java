package com.dexels.navajo;

import com.dexels.navajo.server.GenericHandler;

public class PerformanceTests {

	static int ITERATIONS = 100000;
	
	public static void main(String [] args ) throws Exception {
		
		String className = "com.dexels.navajo.server.GenericHandler";
		
		long start = System.currentTimeMillis();
		for ( int i = 0; i < ITERATIONS; i++ ) {
			Class c = Class.forName(className);
			Object o = c.newInstance();
		}
		System.err.println("newInstance(): " + ITERATIONS + " took " + ( System.currentTimeMillis() - start ) + " millis.");
		
		start = System.currentTimeMillis();
		for ( int i = 0; i < ITERATIONS; i++ ) {
			GenericHandler gh = new GenericHandler();
		}
		System.err.println("direct new(): " + ITERATIONS + " took " + ( System.currentTimeMillis() - start ) + " millis.");
		
	}
}
