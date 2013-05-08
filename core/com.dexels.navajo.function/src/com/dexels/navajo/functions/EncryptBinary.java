package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.security.Security;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class EncryptBinary extends FunctionInterface {

	@Override
	public String remarks() {
		return "Encrypts a binary using a 128 bit key";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		String result = null;
		
		String key = (String) getOperand(0);
		Binary image = (Binary) getOperand(1);
		
		try {
			Security s = new Security(key);
			result = s.encrypt(image).replace("\n", "");
		} catch (Exception e) {
			throw new TMLExpressionException(e.getMessage());
		}
		
		return result;
	}

	public static void main(String [] args) throws Exception {
		
		EncryptBinary e = new EncryptBinary();
		e.reset();
		e.insertOperand("BBFW06E");
		e.insertOperand(new Binary("Apenoot".getBytes()));
		String result = (String) e.evaluate();
		System.err.println("result: " + result);
	}
}
