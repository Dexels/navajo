package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public interface RemoteStorage {
	public long getRemoteModificationDate(String location) throws IOException;

	public InputStream getContents(String location, Map<String, Object> metaData)
			throws IOException;

	public URL getURL(String location) throws IOException;

}
