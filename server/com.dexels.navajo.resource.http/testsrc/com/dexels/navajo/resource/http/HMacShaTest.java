/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.http;

import org.apache.commons.codec.digest.HmacUtils;
import org.junit.Test;

public class HMacShaTest {

	public HMacShaTest() {
	}

	@Test
	public void testHMAC() {
		long expirationTime = 1513890085;
		String bucket = "example";
		String id = "test1.png";
		String secret = "fc6a0bd4d36da7a47fa8d4";
		String path = Long.toString(expirationTime)+"/"+bucket+"/"+id;
		String encoded = HmacUtils.hmacSha1Hex(secret, path);
		System.err.println("Encoded: "+encoded);
	}

	@Test
	public void testConstructedHMAC() {
		long expirationTime = 1524525148; // Instant.now().getEpochSecond()+10000;
		String bucket = "example";
		String id = "cake.png";
		String secret = "fc6a0bd4d36da7a47fa8d4";
		String path = Long.toString(expirationTime)+"/"+bucket+"/"+id;
		String encoded = HmacUtils.hmacSha1Hex(secret, path);
		System.err.println("Encoded: "+encoded+" expire: "+expirationTime);
	}

}
