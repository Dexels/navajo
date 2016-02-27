package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface ClassLoaderStorage {
	public boolean hasLocal(String location) throws IOException;

	public InputStream getLocalData(String location) throws IOException;

	public URL getURL(String location, InputStream is) throws IOException;
	
	public void invalidate(String location);
}
