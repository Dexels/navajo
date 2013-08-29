package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.MeasuredInputStream;
import com.dexels.navajo.client.stream.TransferDataListener;

public class HttpResourceLoader extends ClassPathResourceLoader implements Serializable, TransferDataListener {

	private static final long serialVersionUID = 2074289039171755441L;
	private final URL baseURL;
	private long total = 0;
	private int connections = 0;
	private int failed = 0;
	private long duration;
	private String description;
	
	private final static Logger logger = LoggerFactory
			.getLogger(HttpResourceLoader.class);
	
	public HttpResourceLoader(String baseLocation, String description) throws MalformedURLException {
		this.baseURL = new URL(baseLocation);
		this.description = description;
	}

	public URL getResourceURL(String location) throws MalformedURLException {
		URL u = new URL(baseURL, location);
		return u;
	}

	public InputStream getResourceStream(String location) throws IOException {
		URL u = getResourceURL(location);
		InputStream is = null;
		URLConnection uc = u.openConnection();
		uc.addRequestProperty("Accept-Encoding", "gzip");
		try {
			is = uc.getInputStream();
		} catch (FileNotFoundException e) {
			logger.debug("Resource: {} missing but trying super.",location);
		} catch (IOException e) {
			logger.debug("Error but trying super method: ",e);
		}
		final String contentEncoding = uc.getContentEncoding();
		if (is != null) {
			return openStream(location, is, contentEncoding);
		}
		long started = System.currentTimeMillis();
		InputStream classLoaderInputStream = super.getResourceStream(location);
		if(classLoaderInputStream==null) {
			transferFailed(location,(System.currentTimeMillis() - started));
		}
		return classLoaderInputStream;
	}

	private InputStream openStream(String location,InputStream is, final String contentEncoding)
			throws IOException {
		if ("gzip".equals(contentEncoding)) {
			logger.info(">>>>>>> zipped");
			return new GZIPInputStream(new MeasuredInputStream(this,description+ " : "+ location, is));
		}

		return new MeasuredInputStream(this,description+ " : "+ location, is);
	}

	public List<File> getAllResources() throws IOException {
		throw new UnsupportedOperationException(
				"The http resource loader is unable to enumerate resources");
	}

	@Override
	public void transferCompleted(String label, long bytes, long duration) {
		total+=bytes;
		this.connections++;
		this.duration+= duration;
		report();
	}

	@Override
	public void transferFailed(String label, long duration) {
		this.connections++;
		this.failed ++;
		this.duration+= duration;
		logger.warn("Failed load: "+label,new Exception());
		report();
	}
	
	private void report() {
		logger.info("Loader: "+ description+" network: "+total+" connections: "+connections+" failed: "+failed+" duration: "+duration+" millis since start: "+(System.currentTimeMillis() - com.dexels.navajo.tipi.TipiContext.contextStartup));
	}
}
