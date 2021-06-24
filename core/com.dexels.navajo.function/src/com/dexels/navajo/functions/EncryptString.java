/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.security.Security;

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
		e.insertStringOperand(key);
		e.insertStringOperand(message);
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
