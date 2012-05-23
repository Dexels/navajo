package com.dexels.navajo.client.nql;

import java.io.OutputStream;

public interface OutputCallback {
	public OutputStream getOutputStream();
	public void setOutputType(String mime);
	public void setContentLength(long l);
}
