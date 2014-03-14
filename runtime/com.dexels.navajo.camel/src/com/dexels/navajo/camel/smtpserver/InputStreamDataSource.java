package com.dexels.navajo.camel.smtpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class InputStreamDataSource implements DataSource {

	private final InputStream inputStream;

	public InputStreamDataSource(InputStream b) {
		this.inputStream = b;
	}
	
	@Override
	public String getContentType() {
		return "application/unknown";
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}

	@Override
	public String getName() {
		return "anonymous stream";
	}

	/**
	 * This will append to the existing binary. Might not be what you want.
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}

}
