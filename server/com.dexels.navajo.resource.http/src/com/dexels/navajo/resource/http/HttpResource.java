package com.dexels.navajo.resource.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.concurrent.Future;

public interface HttpResource {
	public InputStream call() throws MalformedURLException, IOException;
	public InputStream call(OutputStream os) throws MalformedURLException, IOException;
	public Future<InputStream> callAsync();
	public Future<InputStream> callAsync(OutputStream os);
	public String getURL();
}
