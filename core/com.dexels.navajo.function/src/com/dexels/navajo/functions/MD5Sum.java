/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * @author Jarno Posthumus
 */
public class MD5Sum extends FunctionInterface {

	public MD5Sum() {
	}

	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		String output = "unknown";
		if (getOperand(0) == null) {
			return Integer.valueOf(0);
		}
		if ( getOperand(0) instanceof Binary ) {
			Binary binaryFile = (Binary) getOperand(0);	
			return binaryFile.getHexDigest();
		}
		
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		if ( getOperand(0) instanceof Message ) { 
			Message m = (Message) getOperand(0);
			StringWriter sw = new StringWriter();
			m.write(sw);
			md5.update(sw.toString().getBytes());
		} else {
			md5.update((getOperand(0)+"").getBytes());	
		}
		
		byte[] array = md5.digest();
		if (getOperands().size() > 1 && getOperand(1) instanceof Boolean && (boolean) getOperand(1)) {
			// return hex representation
			return new BinaryDigest(array).hex();
		}
		BigInteger bigInt = new BigInteger(1, array);
		output = bigInt.toString(16);
		
		return output;

	}

	@Override
	public String remarks() {
		return "Get the MD5Sum of supplied Binary object.";
	}

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception {

		String b = "Kroket";
		MD5Sum fsc = new MD5Sum();
		fsc.reset();
		fsc.insertStringOperand(b);
		Object o = fsc.evaluate();
		System.err.println("o  = " + o);

		Navajo nf = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(nf, "Aap");
		nf.addMessage(m);
		Property p = NavajoFactory.getInstance().createProperty(nf, "Noot", "string", "nene", 0, "", "in");
		m.addProperty(p);
		nf.write(System.err);
		
		fsc.reset();
		fsc.insertMessageOperand(m);
		Object mo = fsc.evaluate();
		System.err.println("mo  = " + mo);
		
		// 42a03618fd3a5e1cecfbbd13906081a4 (Kibbeling)
		// 1d3183a7b9b18f9777649b6a8f9057e3 (Kibbeling with secret key).
		// ab84d2d19db5d5a2cd9f6a96a260caa3 (Kroket).
		// 2035c3091f6970164c04b7ce279d45b1 (Message).
		
	}
}
