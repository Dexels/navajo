package com.dexels.navajo.parser.compiled.api;

public class CacheSubexpression {
	
	private static boolean cacheSubExpression; 
	static {
		String env = System.getenv("CACHE_SUBEXPRESSION");
		if(env!=null && !env.toLowerCase().equals("false")) {
			cacheSubExpression  = true;
		} else {
			cacheSubExpression = false;
		}
	}
	
	public static boolean getCacheSubExpression() {
		return cacheSubExpression;
	}
	public static void setCacheSubExpression(boolean b) {
		cacheSubExpression = b;
	}
}
