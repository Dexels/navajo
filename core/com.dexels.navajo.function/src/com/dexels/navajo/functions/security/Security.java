package com.dexels.navajo.functions.security;

import java.nio.charset.Charset;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.dexels.utils.Base64;

import com.dexels.navajo.document.types.Binary;

public class Security {

	private static final String ALGO = "AES";
	private byte[] keyValue = null;

	public Security(String secret) {
		// Transform secret to 128 bit key.
		if ( secret.length() > 16 ) {
			secret = secret.substring(0, 16);
		} else if ( secret.length() < 16 ) {
			int missing = 16 - secret.length();
			for ( int i = 0; i < missing; i++ ) {
				secret = secret + Character.toString((char) (i % 10 + 80 + secret.length()));
			}
		}
		keyValue = secret.getBytes(Charset.forName("US-ASCII"));
	}
	
	public String encrypt(Binary data) throws Exception {
		
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(data.getData());
		
		String encryptedValue = Base64.encode(encVal);
		return encryptedValue;
		
	}
	
	public String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		
		String encryptedValue = Base64.encode(encVal);
		return encryptedValue;
	}

	public Binary decryptBinary(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		Binary b = new Binary(decValue);
		return b;
	}
	
	public String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}
	
	public Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
    public static void main(String[] args) throws Exception {

        String password = "mypassword";
        Security s = new Security("%#'/'_+!@");
        String passwordEnc = s.encrypt(password);
        String passwordDec = s.decrypt(passwordEnc);

        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted Text : " + passwordEnc);
        System.out.println("Decrypted Text : " + passwordDec);
    }

}
