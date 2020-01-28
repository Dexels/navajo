package com.dexels.navajo.script.api;

import java.io.IOException;

import javax.servlet.ServletRequest;

import com.dexels.navajo.document.Navajo;

public interface AsyncRequest {

	public static final String COMPRESS_GZIP = "gzip";
	public static final String COMPRESS_JZLIB = "jzlib";
//	public static final String COMPRESS_NONE = "";

	public ClientInfo createClientInfo(long scheduledAt, long startedAt,
			int queueLength, String queueId);

	public Object getCert();
	
	public void writeOutput(Navajo inDoc, Navajo outDoc, long scheduledAt,
			long startedAt, String threadStatus) throws IOException;

	public void fail(Exception e);

	public void endTransaction() throws IOException;

	public Navajo getInputDocument();

	public ServletRequest getHttpRequest();

	public long getConnectedAt();

	public String getUrl();
	
	public String getIpAddress();

	public String getInstance();

}
