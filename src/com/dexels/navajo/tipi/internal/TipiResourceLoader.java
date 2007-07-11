package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;

public interface TipiResourceLoader {
	public URL getResourceURL(String location) throws MalformedURLException;

	public InputStream getResourceStream(String location) throws IOException;
}
