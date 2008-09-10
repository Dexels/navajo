package com.dexels.navajo.tipi.internal.cache;

import java.io.*;
import java.net.*;

public interface RemoteStorage {
	public long getRemoteModificationDate(String location) throws IOException;

	public InputStream getContents(String location) throws IOException;

	public URL getURL(String location) throws IOException;

}
