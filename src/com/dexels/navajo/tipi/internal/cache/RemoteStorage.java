package com.dexels.navajo.tipi.internal.cache;

import java.io.*;
import java.net.*;
import java.util.Map;

public interface RemoteStorage {
	public long getRemoteModificationDate(String location) throws IOException;

	public InputStream getContents(String location, Map<String,Object> metaData) throws IOException;

	public URL getURL(String location) throws IOException;

}
