package com.dexels.navajo.server.listener.http;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.script.api.TmlRunnable;

public interface AsyncRequest {

	public void appendData(byte[] buffer, int readCount) throws IOException;

	public void setRequestSize(int contentLength);

	public int getRequestSize();

	public int getReadCount();

	// public void setReadCount(int i);
	public void dumpBuffer();

	public TmlRunnable getTmlRunnable();

	public long getEndEventCount();

	public void updateEndEventCount();

	// public OutputStream getRequestOutputStream() throws IOException;
	public InputStream getRequestInputStream() throws IOException;

	public void submitComplete();

}
