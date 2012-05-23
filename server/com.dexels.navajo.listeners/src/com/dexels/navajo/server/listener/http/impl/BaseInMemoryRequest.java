package com.dexels.navajo.server.listener.http.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.dexels.navajo.server.listener.http.AsyncRequest;

public abstract class BaseInMemoryRequest implements AsyncRequest {

	private ByteArrayOutputStream os = new ByteArrayOutputStream(); // = new
																	// ByteArrayOutputStream();
	private int readCount = 0;
	private int contentLength;

	// private File tempFile;

	protected BaseInMemoryRequest() {
	}

	public void appendData(byte[] buffer, int length) throws IOException {
		getRequestOutputStream().write(buffer, 0, length);
		readCount += length;
		// getRequestOutputStream().flush();

	}

	public int getReadCount() {
		return readCount;
	}

	//
	//
	// public void setReadCount(int readCount) {
	// this.readCount = readCount;
	// }
	//

	public OutputStream getRequestOutputStream() throws IOException {
		return os;
	}

	public InputStream getRequestInputStream() throws IOException {
		getRequestOutputStream().flush();
		getRequestOutputStream().close();
		// FileInputStream fis = new FileInputStream(tempFile);
		return new ByteArrayInputStream(os.toByteArray());
	}

	@Override
	public void dumpBuffer() {

	}

	public int getRequestSize() {
		return this.contentLength;
	}

	public void setRequestSize(int contentLength) {
		this.contentLength = contentLength;
	}

	public void submitComplete() {
		// TODO Delete temp file!
	}

}
