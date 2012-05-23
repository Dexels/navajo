package com.dexels.navajo.tipi.vaadin.components.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.vaadin.terminal.StreamResource.StreamSource;

public class URLInputStreamSource implements StreamSource {

	private static final long serialVersionUID = -3296924859018619919L;
	private final URL url;
	
	public URLInputStreamSource(URL url) {
		this.url = url;
	}
	
	@Override
	public InputStream getStream() {
		try {
			return url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
