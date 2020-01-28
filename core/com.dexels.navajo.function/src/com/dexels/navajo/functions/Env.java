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
