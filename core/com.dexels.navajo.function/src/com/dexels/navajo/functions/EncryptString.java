package com.dexels.navajo.functions;

import com.dexels.navajo.functions.security.Security;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class EncryptString extends FunctionInterface {

	@Override
	public String remarks() {
		return "Encrypts a message using a 128 bit key";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		String result = null;
		
		String key = (String) getOperand(0);
		String message = (String) getOperand(1);
		
		try {
			Security s = new Security(key);
			result = s.encrypt(message).replace("\n", "");
		} catch (Exception e) {
			throw new TMLExpressionException(e.getMessage());
		}
		
		return result;
	}

	public static void main(String [] args) throws Exception {
		
		EncryptString e = new EncryptString();
		e.reset();
		e.insertOperand("d3X3lS!");
		e.insertOperand("testtest");
		String result = (String) e.evaluate();
		System.err.println("result: " + result);
	}
}
