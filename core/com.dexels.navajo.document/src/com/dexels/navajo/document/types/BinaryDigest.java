/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.types;

import java.util.Arrays;

public final class BinaryDigest {

	private final byte[] digest;

	public BinaryDigest(byte[] digest) {
		if(digest==null) {
			throw new NullPointerException("Cant make a digest out of null");
		}
		this.digest = Arrays.copyOf(digest, digest.length);
	}

	public BinaryDigest(String digestHex) {
		this.digest = hexStringToByteArray(digestHex);
	}

	private byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	public String toString() {
		return hex();
	}
	
    public String hex() {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[digest.length * 2];
        int v;
        for ( int j = 0; j < digest.length; j++ ) {
            v = digest[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
