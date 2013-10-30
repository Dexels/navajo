package com.dexels.navajo.tipi.internal.swing.cache.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

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

	private static final String MODMAP_KEY = "modificationmap";
	private static final long DEFAULT_SIZE = 1000000;
	private final PersistenceService ps;
	private final BasicService bs;

	private final String cacheBase = "tipicache_";
	private final String relativePath;
	
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpLocalStorage.class);
	
	
	private final Map<String, Long> localModificationMap = new HashMap<String, Long>();

	public JnlpLocalStorage(String relativePath, CookieManager cm)
			throws UnavailableServiceException {
		ps = (PersistenceService) ServiceManager
				.lookup("javax.jnlp.PersistenceService");
		bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
		this.relativePath = relativePath.replaceAll("/", "_");
		try {
			parseModMap();
		} catch (IOException e) {
			logger.error("Error detected",e);
		}
	}

	private void parseModMap() throws IOException {
		InputStream is = getLocalData(MODMAP_KEY);
		if (is == null) {
			// never mind
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		line = br.readLine();
		do {
			if (line.equals("")) {
				line = br.readLine();
				break;
			}
			if (line.indexOf("|") == -1) {
				line = br.readLine();
				break;

			}
			StringTokenizer st = new StringTokenizer(line, "|");
			String path = st.nextToken();
			long l = Long.parseLong(st.nextToken());
			localModificationMap.put(path, l);
			line = br.readLine();
		} while (line != null);

		is.close();
	}

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
		ps.delete(createMuffinUrl(location));
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
			fc = ps.get(createMuffinUrl(location));
			if (fc == null) {
				return null;
			}
			return fc.getInputStream();
		} catch (MalformedURLException e) {
			logger.error("Error detected",e);
		} catch (FileNotFoundException e) {
			// regular cache miss
			return null;
		} catch (IOException e) {
			logger.error("Error detected",e);

		}
		return null;
	}

	@Override
	public long getLocalModificationDate(String location) throws IOException {
		Long mod = localModificationMap.get(location);
		if (mod == null) {
			return -1;
		}
		return mod;
		// return 0;
	}

	@Override
	public URL getURL(String location) throws IOException {
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
			ff.setMaxLength(length);

		} catch (FileNotFoundException e) {
			// logger.debug("Not found. fine.");
		}
		if (ff == null) {
			ps.create(muffinUrl, length);
		}

		fc = ps.get(muffinUrl);
		OutputStream os = fc.getOutputStream(true);
		copyResource(os, data);
		if (!location.equals(MODMAP_KEY)) {
			localModificationMap.put(location, System.currentTimeMillis());
			// logger.debug("Data saved and modentry added, Local modmap size: "+localModificationMap.size());
			saveModMap();
		}
		// throw new IOException("JNLP Storage not yet implemeented");
	}

	private void saveModMap() {
		// logger.debug("Saving modmap: "+localModificationMap);
		Set<Entry<String, Long>> eset = localModificationMap.entrySet();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Long> entry : eset) {
			sb.append(entry.getKey());
			sb.append("|");
			sb.append(entry.getValue());
			sb.append("\n");
		}
		// logger.debug("Modmap: "+sb.toString());
		byte[] bytes = sb.toString().getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Map<String, Object> meta = new HashMap<String, Object>();
		meta.put("length", bytes.length);
		try {
			storeData(MODMAP_KEY, bais, meta);
			bais.close();
		} catch (IOException e) {
			logger.error("Error detected",e);
		}
		// myCookieMananger.setCookie(MODMAP_KEY, sb.toString());

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
		// TODO Auto-generated method stub
		
	}

}
