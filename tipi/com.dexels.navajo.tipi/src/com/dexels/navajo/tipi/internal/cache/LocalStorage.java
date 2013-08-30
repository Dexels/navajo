package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public interface LocalStorage {
	public boolean hasLocal(String location) throws IOException;

	public long getLocalModificationDate(String location) throws IOException;

	public InputStream getLocalData(String location) throws IOException;

	public void storeData(String location, InputStream data,
			Map<String, Object> metadata) throws IOException;

	public void flush(String location) throws IOException;

	public void flushAll() throws IOException;

	public URL getURL(String location) throws IOException;
	
	public boolean hasLocalWithDigest(String location, byte[] digest);
}
