package com.dexels.navajo.article;

import java.io.InputStream;

public class DirectOutputThrowable extends Exception {

	private static final long serialVersionUID = 476969437269688913L;
	private final InputStream stream;
	private final String mimeType;

	public DirectOutputThrowable(String mimetype, InputStream is) {
		this.stream = is;
		this.mimeType = mimetype;
	}

	public InputStream getStream() {
		return stream;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	
}
