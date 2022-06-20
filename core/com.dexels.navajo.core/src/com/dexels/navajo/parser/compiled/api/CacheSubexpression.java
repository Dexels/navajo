/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
