package com.dexels.navajo.tipi.internal.cache;

import java.io.*;
import java.net.*;

public interface CacheManager {
	public boolean hasLocal(String location) throws IOException;
	public InputStream getContents(String data) throws IOException;
	public boolean isUpToDate(String location) throws IOException;
	public URL getRemoteURL(String location) throws IOException;
	public URL getLocalURL(String location) throws IOException;
}
