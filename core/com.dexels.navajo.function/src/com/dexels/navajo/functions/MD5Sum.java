package com.dexels.navajo.functions;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

		Binary binaryFile = (Binary) getOperand(0);

		MessageDigest md5 = null;

		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(binaryFile.getData());

			byte[] array = md5.digest();

			BigInteger bigInt = new BigInteger(1, array);
			output = bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		return output;

	}

	public String remarks() {
		return "Get the MD5Sum of supplied Binary object.";
	}

	public static void main(String args[]) throws Exception {

		Binary b = new Binary(new java.io.FileInputStream("C:/feyenoord-goud.png"));
		MD5Sum fsc = new MD5Sum();
		fsc.reset();
		fsc.insertOperand(b);
		Object o = fsc.evaluate();
		System.err.println("o = " + o);

	}
}
