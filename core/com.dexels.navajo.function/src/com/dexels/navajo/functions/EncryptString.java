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

	static String encrypt(String key, String message) throws TMLExpressionException {
		EncryptString e = new EncryptString();
		e.reset();
		e.insertOperand(key);
		e.insertOperand(message);
		String result = (String) e.evaluate();
		return result;
	}
	public static void main(String [] args) throws Exception {
		
		final String key = "d3X3lS!";
		final String message = "testtest";
		String encrypted = encrypt(key, message);
		System.err.println("encrypted: "+encrypted);
		String res = DecryptString.decrypt(key, encrypted);
		System.err.println("decrypted: "+res);
	}
}
