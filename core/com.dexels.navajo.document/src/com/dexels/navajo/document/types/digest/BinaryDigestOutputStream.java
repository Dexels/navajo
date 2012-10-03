package com.dexels.navajo.document.types.digest;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;

import com.dexels.navajo.document.types.Binary;

public class BinaryDigestOutputStream extends DigestOutputStream {

	private final Binary binary;
	
	public BinaryDigestOutputStream(Binary b, OutputStream stream, MessageDigest digest) {
		super(stream, digest);
		binary = b;
	}

	@Override
	public void close() throws IOException {
		super.close();
//		binary.setDigest(super.getMessageDigest().digest());
	}

}
