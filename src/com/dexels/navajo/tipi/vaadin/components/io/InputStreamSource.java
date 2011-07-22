package com.dexels.navajo.tipi.vaadin.components.io;

import java.io.InputStream;

import com.vaadin.terminal.StreamResource.StreamSource;

public class InputStreamSource implements StreamSource {

	private static final long serialVersionUID = -3296924859018619919L;
	private final transient InputStream inputStream;

	public InputStreamSource(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	@Override
	public InputStream getStream() {
		return inputStream;
	}

}
