/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Map;

import com.dexels.navajo.expression.api.FunctionInterface;

public class Env extends FunctionInterface {

	@Override
	public String remarks() {
		  return "Returns the value of an environment variable (if set)";
	}

	@Override
	public Object evaluate() {
		Map<String, String> env = System.getenv();
		String key = getStringOperand(0);
		return env.get(key);
	}
	
	public static void main(String [] args) throws Exception {
		Env e = new Env();
		e.reset();
		e.insertStringOperand("HOSTNAME");
		String s = (String) e.evaluate();
		System.err.println("e: " + s);
	}

}
