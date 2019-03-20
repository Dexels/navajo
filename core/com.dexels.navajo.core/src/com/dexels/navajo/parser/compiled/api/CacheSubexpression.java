package com.dexels.navajo.parser.compiled.api;

public class CacheSubexpression {
	
	private static boolean cacheSbExpression; 
	static {
		String env = System.getenv("CACHE_SUBEXPRESSION");
		if(env!=null && !env.equalsIgnoreCase("false")) {
			cacheSbExpression  = true;
		} else {
			cacheSbExpression = false;
		}
	}
	
	public static boolean getCacheSubExpression() {
		return cacheSbExpression;
	}
	public static void setCacheSubExpression(boolean b) {
		cacheSbExpression = b;
	}
}
