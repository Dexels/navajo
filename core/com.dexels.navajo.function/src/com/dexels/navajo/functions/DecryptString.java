package com.dexels.navajo.functions;

import com.dexels.navajo.functions.security.Security;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class DecryptString extends FunctionInterface {

	@Override
	public String remarks() {
		return "Encrypts a message using a 128 bit key";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		String result = null;
		
		String key = (String) getOperand(0);
		String message = (String) getOperand(1);
		System.err.println("Decrypting: |"+key+"|");
		
		try {
			Security s = new Security(key);
			result = s.decrypt(message);
		} catch (Exception e) {
			throw new TMLExpressionException(e.getMessage(),e);
		}
		
		return result;
	}

	static String decrypt(String key, String message) throws TMLExpressionException {
		DecryptString es = new DecryptString();
		es.reset();
		es.insertOperand(key);
		es.insertOperand(message);
		String result = (String) es.evaluate();
		return result;
	}
	
	public static void main(String [] args) throws Exception {
		
//		String s = "6jyZodUTXHmq5vR36F3Sf8AQw5Eil4Hubn0sEdAas4nOV4Fkh9vtuSJGQZqsEJDpIV+XnvNoaL7EPjhzy/AlLn9ZLmyFTxbLcC79a/quGHo=";
		String s = "5CO5fUgfoGOQMus628uG/Ip5jzSrLGVKAdP4rElwJm7/BijV4ph76b1K/f68yb1yaIymuih4ane9H/qdmkVUnU0Q+Ar5gZSuRCtwCKPcs6E=";
		DecryptString e = new DecryptString();
		e.reset();
		e.insertOperand("aap123aap123");
		e.insertOperand(s);
		decrypt("aap123aap123", s);
		String result = (String) e.evaluate();
		System.err.println("result: " + result);
	}
}
