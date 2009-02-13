package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import com.dexels.navajo.tipi.internal.cache.*;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class JnlpLocalStorage implements LocalStorage {

	private static final String MODMAP_KEY = "modificationmap";
	private static final long DEFAULT_SIZE = 1000000;
	private final PersistenceService ps;
	private final BasicService bs;

	private final String cacheBase="tipicache_";
	private final String relativePath;
	private final CookieManager myCookieMananger;
	
	private final Map<String,Long> localModificationMap = new HashMap<String, Long>();
	public JnlpLocalStorage(String relativePath, CookieManager cm) throws UnavailableServiceException {
	        myCookieMananger = cm;
			ps = (PersistenceService)ServiceManager.lookup("javax.jnlp.PersistenceService"); 
	        bs = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 
	        this.relativePath = relativePath.replaceAll("/", "_");
			try {
				parseModMap();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void parseModMap() throws IOException {
		InputStream is = getLocalData(MODMAP_KEY);
		if(is==null) {
			// never mind
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		line = br.readLine();
		do {
			System.err.println("Parse: "+line);
			if(line.equals("")) {
				line = br.readLine();
				break;
			}
			if(line.indexOf("|")==-1) {
				line = br.readLine();
				break;
				
			}
			StringTokenizer st = new StringTokenizer(line,"|");
			String path = st.nextToken();
			long l = Long.parseLong(st.nextToken());
			localModificationMap.put(path,l);
			line = br.readLine();
		} while(line!=null);

		is.close();
	}
	
	private URL getCacheBaseURL() throws MalformedURLException {
//		return new URL(new URL(bs.getCodeBase(),cacheBase),relativePath);
		return bs.getCodeBase();
	}
	private URL createMuffinUrl(String location) {
		String fixed = cacheBase+relativePath+location.replaceAll("/", "_");  
		try {
			URL url = new URL(getCacheBaseURL(),fixed);
			System.err.println("Muffin url fabricated: "+url.toString());
			return url;
		} catch (MalformedURLException e) {
			System.err.println("Muffin url storage problem!");
			e.printStackTrace();
			return null;
		}
	}
	
	public void flush(String location) throws IOException {
		ps.delete(createMuffinUrl(location));
	}

	public void flushAll() throws IOException {
		String[] cacheMuffins = ps.getNames(getCacheBaseURL());
		for (int i = 0; i < cacheMuffins.length; i++) {
			ps.delete(new URL(getCacheBaseURL(),cacheMuffins[i]));
		}
	}

	public InputStream getLocalData(String location) {
		FileContents fc;
		try {
			fc = ps.get(createMuffinUrl(location));
			if(fc==null) {
				return null;
			}
			return fc.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// regular cache miss
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	return null;
	}

	public long getLocalModificationDate(String location) throws IOException {
		System.err.println("Getting moddate for: "+location);
		System.err.println("Modmap: "+localModificationMap);
		Long mod = localModificationMap.get(location);
		System.err.println("Mod: "+mod);
		if(mod==null) {
			return -1;
		}
		return mod;
//		return 0;
	}

	public URL getURL(String location) throws IOException {
		System.err.println("Warning: using getURL is not efficient");
		File f=File.createTempFile("tipiCache", "");
		InputStream is = getLocalData(location);
		OutputStream os = new FileOutputStream(f);
		copyResource(os, is);
		f.deleteOnExit();
		return f.toURI().toURL();
//		throw new IOException("URL GETTING NOT SUPPORTED IN JNLPLOCALSTORAGE!");
	}

	public boolean hasLocal(String location) {
		FileContents fc = null;
		try {
			fc = ps.get(createMuffinUrl(location));
			if(fc!=null) {
				System.err.println("local entry found: "+fc.getLength()+" at location: "+location);
			} else {
				System.err.println("No local entry found at: "+location);
			}
		} catch (MalformedURLException e) {
			System.err.println("Malformed panic blues!");
			
		} catch (FileNotFoundException e) {
			System.err.println("Local file: "+location+" not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(fc==null) {
			return false;
		} else {
			try {
				return fc.getLength()!=0;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	public void storeData(String location, InputStream data,Map<String,Object> metadata) throws IOException {
		FileContents fc = null;
			URL muffinUrl = createMuffinUrl(location);
			FileContents ff = null;
		//	Object l = (Object) metadata.get("length");
	//		if(l )
			//long length = l==null?DEFAULT_SIZE:l;
			long length = DEFAULT_SIZE;
			System.err.println("JNLP storage. Storing: "+length+" bytes.");
			
			try {
				ff = ps.get(muffinUrl);
				ff.setMaxLength(length);
				
			} catch (FileNotFoundException e) {
				System.err.println("Not found. fine.");
			}
			if(ff==null) {
				long res = ps.create(muffinUrl,length);
			}
			
			fc = ps.get(muffinUrl);
			OutputStream os = fc.getOutputStream(true);
			copyResource(os, data);
			if(!location.equals(MODMAP_KEY)) {
				localModificationMap.put(location,System.currentTimeMillis());
				System.err.println("Data saved and modentry added, Local modmap size: "+localModificationMap.size());
				saveModMap();				
			}
			//			throw new IOException("JNLP Storage not yet implemeented");
	}
	
	private void saveModMap() {
		System.err.println("Saving modmap: "+localModificationMap);
		Set<Entry<String,Long>> eset = localModificationMap.entrySet();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Long> entry : eset) {
			sb.append(entry.getKey());
			sb.append("|");
			sb.append(entry.getValue());
			sb.append("\n");
		}
		System.err.println("Modmap: "+sb.toString());
		byte[] bytes = sb.toString().getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Map<String,Object> meta = new HashMap<String, Object>();
		meta.put("length", bytes.length);
		try {
			storeData(MODMAP_KEY, bais,meta);
			bais.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//myCookieMananger.setCookie(MODMAP_KEY, sb.toString());
		
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
//		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) > -1) {
			System.err.println("Read: "+read+" bytes from class: "+in);
			bout.write(buffer, 0, read);
		}
		in.close();
		bout.flush();
		bout.close();
	}

}
