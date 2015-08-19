package com.dexels.navajo.tipi.internal.swing.cache.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class JnlpLocalStorage implements LocalStorage {

	private static final long DEFAULT_SIZE = 1000000;
	private final PersistenceService ps;
	private final BasicService bs;

	private final String cacheBase = "tipicache_";
	private final String relativePath;
	
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpLocalStorage.class);
	
	
//	private final Map<String, Long> localModificationMap = new HashMap<String, Long>();
	private final String id;

	public JnlpLocalStorage(String relativePath, CookieManager cm, String id)
			throws UnavailableServiceException {
		this.id = id;
		ps = (PersistenceService) ServiceManager
				.lookup("javax.jnlp.PersistenceService");
		bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
		this.relativePath = relativePath.replaceAll("/", "_");
//		try {
//			parseModMap();
//		} catch (IOException e) {
//			logger.error("Error detected",e);
//		}
	}

//	private void parseModMap() throws IOException {
//		InputStream is = getLocalData(MODMAP_KEY);
//		if (is == null) {
//			// never mind
//			return;
//		}
//		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
//		String line = null;
//		line = br.readLine();
//		do {
//			if (line.equals("")) {
//				line = br.readLine();
//				break;
//			}
//			if (line.indexOf("|") == -1) {
//				line = br.readLine();
//				break;
//
//			}
//			StringTokenizer st = new StringTokenizer(line, "|");
//			String path = st.nextToken();
//			long l = Long.parseLong(st.nextToken());
//			localModificationMap.put(path, l);
//			line = br.readLine();
//		} while (line != null);
//
//		is.close();
//	}

	private URL getCacheBaseURL()  {
		// return new URL(new URL(bs.getCodeBase(),cacheBase),relativePath);
		return bs.getCodeBase();
	}

	private URL createMuffinUrl(String location) {
		String fixed = cacheBase + relativePath + location.replaceAll("/", "_");
		try {
			URL url = new URL(getCacheBaseURL(), fixed);
			return url;
		} catch (MalformedURLException e) {
			logger.error("Error detected",e);
			return null;
		}
	}

	@Override
	public void flush(String location) throws IOException {
		delete(location);
	}

	@Override
	public void flushAll() throws IOException {
		String[] cacheMuffins = ps.getNames(getCacheBaseURL());
		for (int i = 0; i < cacheMuffins.length; i++) {
			ps.delete(new URL(getCacheBaseURL(), cacheMuffins[i]));
		}
	}

	@Override
	public InputStream getLocalData(String location) {
		FileContents fc;
		try {
			URL muffinUrl = createMuffinUrl(location);
			logger.info("Reading muffinurl: "+muffinUrl);
			fc = ps.get(muffinUrl);
			if (fc == null) {
				logger.info("Not found");
				return null;
			}
			logger.info("Getting local data from JNLP store: "+location+" length: "+fc.getLength());
			return fc.getInputStream();
		} catch (MalformedURLException e) {
			logger.error("Error detected",e);
		} catch (FileNotFoundException e) {
			// regular cache miss
			logger.info("not found: {} ",location);
			return null;
		} catch (IOException e) {
			logger.error("Error detected",e);

		}
		return null;
	}

	@Override
	public long getLocalModificationDate(String location) throws IOException {
		 return 0;
	}

	@Override
	public URL getURL(String location) throws IOException {
		logger.debug("URL not happy getting "+location);
		File f = File.createTempFile("tipiCache", "");
		InputStream is = getLocalData(location);
		OutputStream os = new FileOutputStream(f);
		copyResource(os, is);
		f.deleteOnExit();
		return f.toURI().toURL();
		// throw new
		// IOException("URL GETTING NOT SUPPORTED IN JNLPLOCALSTORAGE!");
	}

	@Override
	public boolean hasLocal(String location) {
		FileContents fc = null;
		try {
			fc = ps.get(createMuffinUrl(location));
			// if(fc!=null) {
			// logger.debug("local entry found: "+fc.getLength()+" at location: "+location);
			// } else {
			// logger.debug("No local entry found at: "+location);
			// }
		} catch (MalformedURLException e) {
			// logger.debug("Malformed panic blues!");
			logger.error("Error detected",e);
		} catch (FileNotFoundException e) {
			// logger.debug("Local file: "+location+" not found!");
		} catch (IOException e) {
			logger.error("Error detected",e);
		}
		if (fc == null) {
			return false;
		} else {
			try {
				return fc.getLength() != 0;
			} catch (IOException e) {
				logger.error("Error detected",e);
			}
			return false;
		}
	}

	@Override
	public void storeData(String location, InputStream data,
			Map<String, Object> metadata) throws IOException {
		FileContents fc = null;
		URL muffinUrl = createMuffinUrl(location);
		FileContents ff = null;
		// Object l = (Object) metadata.get("length");
		// if(l )
		// long length = l==null?DEFAULT_SIZE:l;
		long length = DEFAULT_SIZE;
		// logger.debug("JNLP storage. Storing: "+length+" bytes.");
		
		try {
			ff = ps.get(muffinUrl);
//			ps.
//			ff.setMaxLength(length);

		} catch (FileNotFoundException e) {
			// logger.debug("Not found. fine.");
		}
		if (ff == null) {
			long grantedSize = ps.create(muffinUrl, length);
		}

		fc = ps.get(muffinUrl);
		OutputStream os = fc.getOutputStream(true);
		copyResource(os, data);

		logger.info("Stored entry into muffinstore. Location: "+location+ " id: "+id+" muffinurl: "+muffinUrl);
		// throw new IOException("JNLP Storage not yet implemeented");
		URL url = new URL(getCacheBaseURL(), cacheBase + relativePath);
		String[] names = ps.getNames(url);
		logger.info("Listing:");
		for (String element : names) {
			logger.info("File: "+element);
		}
	}

	private final void copyResource(OutputStream out, InputStream in)
			throws IOException {
		// BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) > -1) {
			// logger.debug("Read: "+read+" bytes from class: "+in);
			bout.write(buffer, 0, read);
		}
		in.close();
		bout.flush();
		bout.close();
	}

	@Override
	public void delete(String location) {
		try {
			ps.delete(createMuffinUrl(location));
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

	}

}
