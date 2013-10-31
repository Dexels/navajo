package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface URLCache {
	public InputStream getStream(URL u) throws IOException;
	public void flush();
		
}
