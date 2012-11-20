package com.dexels.navajo.server.listener.http.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.dexels.navajo.script.api.AsyncRequest;

public abstract class BaseInMemoryRequest implements AsyncRequest {

	private ByteArrayOutputStream os = new ByteArrayOutputStream(); // = new
																	// ByteArrayOutputStream();
	private int readCount = 0;
	private int contentLength;
	
	// private File tempFile;

	protected BaseInMemoryRequest() {
	}

	@Override
	public int getReadCount() {
		return readCount;
	}


	protected OutputStream getRequestOutputStream()  {
		return os;
	}

	@Override
	public InputStream getRequestInputStream() throws IOException {
		getRequestOutputStream().flush();
		getRequestOutputStream().close();
		// FileInputStream fis = new FileInputStream(tempFile);
		byte[] byteArray = os.toByteArray();
		return new ByteArrayInputStream(byteArray);
	}

	@Override
	public int getRequestSize() {
		return this.contentLength;
	}

	@Override
	public void setRequestSize(int contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public void submitComplete() {
		// TODO Delete temp file!
	}

}
