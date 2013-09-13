package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.dexels.utils.Base64;
import org.dexels.utils.Base64.DecodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.LocalStorage;

public class FileLocalStorage implements LocalStorage {

	private static final String LOCAL_DIGEST_PROPERTIES = "digest.properties";
	private static final String REMOTE_DIGEST_PROPERTIES = "remotedigest.properties";
	private final File baseFile;
	private Properties localDigestProperties = new Properties();
	
	private final static Logger logger = LoggerFactory
			.getLogger(FileLocalStorage.class);
	
	private MessageDigest createDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			logger.warn("Failed creating messageDigest in binary. Expect problems", e1);
			return null;
		}
	}    

	
	public FileLocalStorage(File base) {
		this.baseFile = base;
		if(!baseFile.exists()) {
			baseFile.mkdirs();
		}
		loadDigestFile(LOCAL_DIGEST_PROPERTIES);
		loadDigestFile(REMOTE_DIGEST_PROPERTIES);
	}


	private void loadDigestFile(String file) {
		File digestProperties = new File(baseFile,file);
		if(digestProperties.exists()) {
			InputStream fos = null;
			try {
				fos = new FileInputStream(digestProperties);
				localDigestProperties.load(fos);
			} catch (IOException e) {
				logger.warn("Error opening digest file. Corrupt? Throwing file away.");
				localDigestProperties.clear();
				digestProperties.delete();
			} finally {
				if(fos!=null) {
					try {
						fos.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	@Override
	public void flush(String location) {
		if (hasLocal(location)) {
			File f = new File(baseFile, convertPath(location));
			f.delete();
		}
	}

	@Override
	public void flushAll() {
	}

	@Override
	public InputStream getLocalData(String location) throws IOException {
		if (hasLocal(location)) {
			File f = new File(baseFile, convertPath(location));
			FileInputStream fis = new FileInputStream(f);
			return fis;
		}
		return null;
	}

	
	
	@Override
	public long getLocalModificationDate(String location) throws IOException {
		if (hasLocal(location)) {
			File f = new File(baseFile, convertPath(location));
			return f.lastModified();
		}
		throw new IOException("File not found. Shouldn't happen");
	}

	@Override
	public boolean hasLocal(String location) {
		File f = new File(baseFile, convertPath(location));
		return f.exists();
	}

	@Override
	public void storeData(String location, InputStream data,
			Map<String, Object> metadata) throws IOException {
		File f = new File(baseFile, convertPath(location));
		FileOutputStream fos = new FileOutputStream(f);
		MessageDigest md = createDigest();
		DigestOutputStream dos = new DigestOutputStream(fos, md);
		copyResource(dos, data);
		
		storeDigest(location,md.digest());
	}

	private void storeDigest(String location, byte[] digest) {
		String digestString = Base64.encode(digest);
		System.err.println("DIGEST location: "+location+" "+digestString);
		localDigestProperties.put(location, digestString.trim());
		storeDigestProperties();
	}

	private void storeDigestProperties() {
		File out = new File(baseFile,LOCAL_DIGEST_PROPERTIES);
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(out);
			localDigestProperties.store(fos, "Locally calculated digests");
		} catch (IOException e) {
			logger.error("Error writing local digest: ", e);
		} finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private final void copyResource(OutputStream out, InputStream in)
			throws IOException {
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

	public String convertPath(String location) {
		return location.replaceAll("/", "_");
	}

	@Override
	public URL getURL(String location) throws IOException {
		File f = new File(baseFile, convertPath(location));
		return f.toURI().toURL();
	}


//	public boolean hasLocalWithDigest(String location, byte[] digest) {
//		String digestString = (String) localDigestProperties.get(location);
//		if(digestString==null) {
//			logger.debug("No digest found. Not using local digest");
//		}
//		try {
//			byte[] found = Base64.decode(digestString);
//			return Arrays.equals(found, digest);
//		} catch (DecodingException e) {
//			logger.error("Error: ", e);
//		}
//		return false;
//	}


	@Override
	public void delete(String location) {
		File f = new File(baseFile, convertPath(location));
		if(f.exists()) {
			f.delete();
		}
	}

}
