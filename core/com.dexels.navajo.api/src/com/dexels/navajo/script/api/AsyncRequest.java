package com.dexels.navajo.script.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public interface AsyncRequest {

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
	public static final String COMPRESS_NONE = "";

	public void setRequestSize(int contentLength);

	public int getRequestSize();

	public int getReadCount();

	public InputStream getRequestInputStream() throws IOException;

	public void submitComplete();

	public ClientInfo createClientInfo(long scheduledAt, long startedAt,
			int queueLength, String queueId);

	public Object getCert();

	public void writeOutput(Navajo inDoc, Navajo outDoc, long scheduledAt,
			long startedAt, String threadStatus) throws IOException,
			FileNotFoundException, UnsupportedEncodingException,
			NavajoException;

	public void fail(Exception e);

	public void endTransaction() throws IOException;

	public Navajo getInputDocument();

	public ServletRequest getHttpRequest();

	public long getConnectedAt();

	public String getUrl();

	public TmlRunnable instantiateRunnable();
}
