package com.dexels.navajo.tipi.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;

public class ZipResourceLoader extends ClassPathResourceLoader {

	private static final long serialVersionUID = 7241870701017818845L;
	private File tipiDefFile;
	final int BUFFERSIZE = 2048;
	private ZipFile zipFile;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ZipResourceLoader.class);
	
	public ZipResourceLoader(Binary b) throws IOException {
		InputStream tipiZippedStream = b.getDataAsStream();
		tipiDefFile = File.createTempFile("tipi", "zip");
		tipiDefFile.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(tipiDefFile);
		copyResource(fos, tipiZippedStream);
		zipFile = new ZipFile(tipiDefFile);
	}

	@Override
	public URL getResourceURL(String location) throws IOException {
		logger.info("Trying to locate in zip: " + location);
		ZipEntry z = zipFile.getEntry(location);
		InputStream is;
		try {
			is = zipFile.getInputStream(z);
			File f = File.createTempFile("tipi", "temp");
			f.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(f);
			copyResource(fos, is);
			URL u = f.toURI().toURL();
			if (u == null) {
				return super.getResourceURL(location);
			}
			return u;
		} catch (IOException e) {
			logger.error("Error: ",e);
			logger.info("Not found, delegating to parent: ");
			return super.getResourceURL(location);
		}
	}

	@Override
	public InputStream getResourceStream(String location) throws IOException {
		logger.info("Trying to locate in zip: " + location);
		ZipEntry z = zipFile.getEntry(location);
		InputStream is;
		is = zipFile.getInputStream(z);
		if (is != null) {
			return is;
			// "+location+" base: "+baseFile);
		}
		return super.getResourceStream(location);
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

}
