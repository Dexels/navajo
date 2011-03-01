package com.dexels.navajo.server.listener.http;

import javax.servlet.http.HttpServletRequest;

public interface AsyncReaderCallback {
	public void reportRequestFinished(HttpServletRequest request);
}
