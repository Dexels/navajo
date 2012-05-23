package com.dexels.navajo.tipi.vaadin.components.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.terminal.StreamResource.StreamSource;

public class BufferedInputStreamSource implements StreamSource {

	private static final long serialVersionUID = -3296924859018619919L;
	private final byte[] data;
	
	public BufferedInputStreamSource(byte[] data) {
		this.data = data;
	}
	
	@Override
	public InputStream getStream() {
		return new ByteArrayInputStream(data);
	}

}
