package com.dexels.navajo.functions;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;

/**
 * @author Jarno Posthumus
 */
public class MD5Sum extends FunctionInterface {

	public MD5Sum() {
	}

	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		String output = "unknown";
		if (getOperand(0) == null) {
			return new Integer(0);
		}
		MessageDigest md5 = null;
		// In the future use a suppleid secret key.
		String key = "FDHJKLS43290983FDJSK";
		
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(key.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		if ( getOperand(0) instanceof Binary ) {
			Binary binaryFile = (Binary) getOperand(0);	
			md5.update(binaryFile.getData());
		} else if ( getOperand(0) instanceof Message ) { 
			Message m = (Message) getOperand(0);
			StringWriter sw = new StringWriter();
			m.write(sw);
			md5.update(sw.toString().getBytes());
		} else {
			md5.update((getOperand(0)+"").getBytes());	
		}
		
		byte[] array = md5.digest();
		BigInteger bigInt = new BigInteger(1, array);
		output = bigInt.toString(16);
		
		return output;

	}

	public String remarks() {
		return "Get the MD5Sum of supplied Binary object.";
	}

	public static void main(String args[]) throws Exception {

		String b = "Kroket";
		MD5Sum fsc = new MD5Sum();
		fsc.reset();
		fsc.insertOperand(b);
		Object o = fsc.evaluate();
		System.err.println("o  = " + o);

		Navajo nf = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(nf, "Aap");
		nf.addMessage(m);
		Property p = NavajoFactory.getInstance().createProperty(nf, "Noot", "string", "nene", 0, "", "in");
		m.addProperty(p);
		nf.write(System.err);
		
		fsc.reset();
		fsc.insertOperand(m);
		Object mo = fsc.evaluate();
		System.err.println("mo  = " + mo);
		
		// 42a03618fd3a5e1cecfbbd13906081a4 (Kibbeling)
		// 1d3183a7b9b18f9777649b6a8f9057e3 (Kibbeling with secret key).
		// ab84d2d19db5d5a2cd9f6a96a260caa3 (Kroket).
		// 2035c3091f6970164c04b7ce279d45b1 (Message).
		
	}
}
