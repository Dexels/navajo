package com.dexels.navajo.functions;


/* 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
*/

import java.math.BigInteger;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Utility class to validate IBAN codes.
 * 
 * The IBAN consists of a ISO 3166-1 alpha-2 country code, followed by two check 
 * digits (represented by kk in the examples below), and up to thirty alphanumeric 
 * characters for the domestic bank account number, called the BBAN (Basic Bank 
 * Account Number).
 * 
 * <h1>Exampe usage scenario</h1>
 * <code><pre>IBAN iban = new IBAN("ES2153893489");
 * if (iban.isValid())
 * 		System.out.println("ok");
 * else
 * 		System.out.println("problem with iban: "+iban.getInvalidCause());
 * </pre></code>
 * 
 * @author mgriffa
 * @since 3.3
 * @version $Id$
 */
public class IBAN {
	private static final BigInteger BD_97 = new BigInteger("97");
	private static final BigInteger BD_98 = new BigInteger("98");
	private String invalidCause = null;
	private String iban;
	ResourceBundle countries = ResourceBundle.getBundle("com.dexels.navajo.functions.countries");
		
	/**
	 * Get the IBAN
	 * @return a string with the IBAN
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Set the IBAN
	 * @param iban the IBAN to set
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * Create an IBAN object with the given iban code.
	 * This constructor does not perform any validation on the iban, only 
	 * @param iban
	 */
	public IBAN(String iban) {
		this.iban = iban;
	}
	
	/**
	 * Completely validate an IBAN
	 * Currently validation checks that the length is at least 5 chars:
	 * (2 country code, 2 verifying digits, and 1 BBAN)
	 * checks the country code to be valid an the BBAN to match the verifying digits
	 * 
	 * @return <code>true</code> if the IBAN is found to be valid and <code>false</code> in other case
	 * @throws IllegalStateException if iban is <code>null</code>
	 */
	public boolean isValid() {
		if (this.iban==null)
			throw new IllegalStateException("iban is null");
		invalidCause = null;
		final String code = removeNonAlpha(this.iban);
		final int len = code.length();
		if (len<4) {
			this.invalidCause="Too short (expected at least 4, got "+len+")";
			return false;
		}
		final String country = code.substring(0, 2);
		if (isInValidCode(country)) {
			this.invalidCause = "Invalid ISO country code: "+country;
			return false;
		}
//		int verification;
//		try {
//			verification = new Integer(code.substring(2, 4)).intValue();
//		} catch (NumberFormatException e) {
//			this.invalidCause = "Bad verification code: "+e;
//			return false;
//		}
		
		final StringBuffer bban = new StringBuffer(code.substring(4));
		if (bban.length()==0) {
			this.invalidCause="Empty Basic Bank Account Number";
			return false;
		}
		bban.append(code.substring(0, 4));
		
		
		String workString = translateChars(bban);
		int mod = modulo97(workString);
		if (mod!=1) {
			this.invalidCause = "Verification failed (expected 1 and obtained "+mod+")";
			return false;
		}
		
		return true;
	}
	
	private boolean isInValidCode(String key){
		Set<String> keys = countries.keySet();
		boolean notfound = !keys.contains(key);
		return notfound;
	}
	

	/**
	 * Translate letters to numbers, also ignoring non alphanumeric characters
	 * 
	 * @param bban
	 * @return the translated value
	 */
	public String translateChars(final StringBuffer bban) {
		final StringBuffer result = new StringBuffer();
		for (int i=0;i<bban.length();i++) {
			char c = bban.charAt(i);
			if (Character.isLetter(c)) {
				result.append(Character.getNumericValue(c));
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	/**
	 * 
	 * @param iban
	 * @return the resulting IBAN
	 */
	public String removeNonAlpha(final String iban) {
		final StringBuffer result = new StringBuffer();
		for (int i=0;i<iban.length();i++) {
			char c = iban.charAt(i);
			if (Character.isLetter(c) || Character.isDigit(c) ) {
				result.append(c);
			}
		}
		return result.toString();
	}

	private int modulo97(String bban) {
		BigInteger b = new BigInteger(bban);
		b = b.divideAndRemainder(BD_97)[1];
		b = BD_98.min(b);
		b = b.divideAndRemainder(BD_97)[1];
		return b.intValue();
        //return ((int)(98 - (Long.parseLong(bban) * 100) % 97L)) % 97;
	}

	/**
	 * Get a string with information about why the IBAN was found invalid
	 * @return a human readable (english) string 
	 */
	public String getInvalidCause() {
		return invalidCause;
	}
	
	public static void main(String[] args){
		IBAN iban = new IBAN("GB82WEST12345698765432");
		System.err.println("Valid: " + iban.isValid());
		if(!iban.isValid()){
			System.err.println("Cause: " + iban.getInvalidCause());
		}
		
	}
	
}

