package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.security.Security;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class DecryptBinary extends FunctionInterface {

	@Override
	public String remarks() {
		return "Decrypts a binary using a 128 bit key";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		Binary result = null;
		
		String key = (String) getOperand(0);
		String message = (String) getOperand(1);
		
		try {
			Security s = new Security(key);
			result = s.decryptBinary(message);
		} catch (Exception e) {
			throw new TMLExpressionException(e.getMessage());
		}
		
		return result;
	}

	public static void main(String [] args) throws Exception {
		
		String s = "34PIf6+W/rTMIaGjBHVI4Q==";
		DecryptBinary e = new DecryptBinary();
		e.reset();
		e.insertOperand("BBFW06E");
		e.insertOperand(s);
		Binary result = (Binary) e.evaluate();
		System.err.println("result: " + new String(result.getData()));
	}

}
