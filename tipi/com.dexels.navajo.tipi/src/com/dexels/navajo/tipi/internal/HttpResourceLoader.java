package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class HttpResourceLoader extends ClassPathResourceLoader implements Serializable {

	private static final long serialVersionUID = 2074289039171755441L;
	private final URL baseURL;

	public HttpResourceLoader(String baseLocation) throws MalformedURLException {
		if (!baseLocation.endsWith("/")) {
			System.err.println("Warning, no trailing slash baseLocation: "
					+ baseLocation);
		}

		this.baseURL = new URL(baseLocation);
	}

	public URL getResourceURL(String location) throws MalformedURLException {
		URL u = new URL(baseURL, location);
		// System.err.println("Getting resource!");
		return u;
	}

	public InputStream getResourceStream(String location) throws IOException {
		URL u = getResourceURL(location);
		InputStream is = null;
		URLConnection uc = u.openConnection();
		uc.addRequestProperty("Accept-Encoding", "gzip");
		try {
			is = uc.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (is != null) {
			if ("gzip".equals(uc.getContentEncoding())) {
				return new GZIPInputStream(is);
			}

			return is;
		}
		InputStream classLoaderInputStream = super.getResourceStream(location);
		// if(classLoaderInputStream==null) {
		// System.err.println("HttpResourceLoader failed. Looking in classpath: "
		// + location + " base: " + baseURL+" resolvedurl: "+u);
		// }

		return classLoaderInputStream;
	}

	public List<File> getAllResources() throws IOException {
		throw new UnsupportedOperationException(
				"The http resource loader is unable to enumerate resources");
	}

	public static void main(String[] args) throws MalformedURLException {
		URL u = new URL("http://www.aap.nl");
		URL b = new URL(u, "noot/");
		System.err.println("U: " + u);
		System.err.println("B: " + b);
		URL c = new URL(b, "init.xml");
		System.err.println("C: " + c);
	}
}
