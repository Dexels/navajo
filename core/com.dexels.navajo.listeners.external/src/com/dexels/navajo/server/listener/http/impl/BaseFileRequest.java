package com.dexels.navajo.server.listener.http.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.AsyncRequest;

public abstract class BaseFileRequest implements AsyncRequest {

	private OutputStream os = null; // = new ByteArrayOutputStream();
	private int contentLength;
	private File tempFile;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseFileRequest.class);
	
	protected BaseFileRequest() {
		try {
			tempFile = File.createTempFile("navajoRequest_", ".xml");
			os = new FileOutputStream(tempFile);
		} catch (FileNotFoundException e) {
			logger.error("Error: ", e);
			
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	//
	// public int getReadCount() {
	// return readCount;
	// }
	//
	//
	// public void setReadCount(int readCount) {
	// this.readCount = readCount;
	// }
	//
	//
	private OutputStream getRequestOutputStream() {
		return os;
	}

	@Override
	public InputStream getRequestInputStream() throws IOException {
		getRequestOutputStream().flush();
		getRequestOutputStream().close();
		FileInputStream fis = new FileInputStream(tempFile);
		// return new ByteArrayInputStream(os.toByteArray());
		return fis;
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
