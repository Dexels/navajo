package com.dexels.navajo.functions.mail.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.dexels.navajo.document.types.Binary;

public class BinaryDataSource implements DataSource {

	private final Binary binary;

	public BinaryDataSource(Binary b) {
		this.binary = b;
	}
	
	@Override
	public String getContentType() {
		return binary.getMimeType();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return binary.getDataAsStream();
	}

	@Override
	public String getName() {
		return binary.getHandle();
	}

	/**
	 * This will append to the existing binary. Might not be what you want.
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		return binary.getOutputStream();
	}

}
