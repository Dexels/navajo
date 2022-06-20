/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.security.Security;

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
		e.insertStringOperand("BBFW06E");
		e.insertStringOperand(s);
		Binary result = (Binary) e.evaluate();
		System.err.println("result: " + new String(result.getData()));
	}

}
