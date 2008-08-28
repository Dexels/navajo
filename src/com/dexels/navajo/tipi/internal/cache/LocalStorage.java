package com.dexels.navajo.tipi.internal.cache;

import java.io.*;
import java.net.*;

public interface LocalStorage {
	public boolean hasLocal(String location) throws IOException;

	public long getLocalModificationDate(String location) throws IOException;

	public InputStream getLocalData(String location) throws IOException;

	public void storeData(String location, InputStream data) throws IOException;

	public void flush(String location) throws IOException;

	public void flushAll(String location) throws IOException;

	public URL getURL(String location)  throws IOException;
}
