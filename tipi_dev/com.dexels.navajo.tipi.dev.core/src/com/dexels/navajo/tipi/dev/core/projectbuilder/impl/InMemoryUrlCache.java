package com.dexels.navajo.tipi.dev.core.projectbuilder.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.tipi.dev.core.projectbuilder.URLCache;

public class InMemoryUrlCache implements URLCache {
	private final Map<URL,byte[]> cacheMap = new HashMap<URL, byte[]>();
	@Override
	public InputStream getStream(URL u) throws IOException {
		byte[] b = cacheMap.get(u);
		if(b!=null) {
			return new ByteArrayInputStream(b);
		}
		InputStream is = u.openStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, is);
		b = baos.toByteArray();
		cacheMap.put(u, b);
		return new ByteArrayInputStream(b);
	}
	
	@Override
	public void flush() {
		cacheMap.clear();
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}
}

