package com.dexels.navajo.server.listener.http.impl;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.script.api.AsyncRequest;

public abstract class WrappedRequest implements AsyncRequest {

	private final AsyncRequest myRequest;
	
	protected WrappedRequest(AsyncRequest input) {
		this.myRequest = input;
	}

	public AsyncRequest getRequest() {
		return myRequest;
	}

	@Override
	public final int getReadCount() {
		return myRequest.getReadCount();
	}

	@Override
	public final InputStream getRequestInputStream() throws IOException {
		return myRequest.getRequestInputStream();
	}

	@Override
	public int getRequestSize() {
		return myRequest.getRequestSize();
	}

	@Override
	public final void setRequestSize(int contentLength) {
		this.myRequest.setRequestSize(contentLength);
	}

	@Override
	public final void submitComplete() {
		myRequest.submitComplete();
	}

}
