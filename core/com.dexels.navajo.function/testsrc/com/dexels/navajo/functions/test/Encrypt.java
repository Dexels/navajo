package com.dexels.navajo.functions.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.security.Security;

public class Encrypt {

	@Test
	public void testEncryptBinary() throws Exception {
		
		Security s = new Security("A");
		File f = new File("test");
		FileOutputStream fos = new FileOutputStream(f);
		fos.write("Content".getBytes());
		fos.close();
		
		Binary b = new Binary(new FileInputStream(f));
		
		String enc = s.encrypt(b);
		
		System.err.println("enc: " + enc);
		
		Binary b2 = s.decryptBinary(enc);
		
		BufferedReader is = new BufferedReader(new InputStreamReader(b2.getDataAsStream()));
		String l = is.readLine();
		is.close();
		
		Assert.assertEquals("Content", l);
		
		f.delete();
	}
}
