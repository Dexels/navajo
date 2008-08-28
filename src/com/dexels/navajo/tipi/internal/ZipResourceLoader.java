package com.dexels.navajo.tipi.internal;

import java.io.*;
import java.net.*;
import java.util.zip.*;

import com.dexels.navajo.document.types.*;

public class ZipResourceLoader extends ClassPathResourceLoader {

	//private final Navajo navajoDefinition;
	//private final Property tipiDef;
	private File tipiDefFile;
//	private final Map myResources = new HashMap<String, byte[]>();
	final int BUFFERSIZE = 2048;
	private ZipFile zipFile;

	public ZipResourceLoader(Binary b) throws IOException {
		InputStream tipiZippedStream = b.getDataAsStream();
		//navajoDefinition = NavajoFactory.getInstance().createNavajo(is);
		//tipiDef = navajoDefinition.getProperty("/Aap");
		//InputStream tipiZippedStream = ((Binary) tipiDef.getTypedValue()).getDataAsStream();
		tipiDefFile = File.createTempFile("tipi", "zip");
		tipiDefFile.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(tipiDefFile);
		copyResource(fos, tipiZippedStream);
		zipFile = new ZipFile(tipiDefFile);
	}

	public URL getResourceURL(String location) throws IOException {
		System.err.println("Trying to locate in zip: "+location);
		ZipEntry z = zipFile.getEntry(location);
		InputStream is;
		try {
			is = zipFile.getInputStream(z);
			File f = File.createTempFile("tipi", "temp");
			f.deleteOnExit();
			FileOutputStream fos =  new FileOutputStream(f);
			copyResource(fos, is);
			URL u = f.toURI().toURL();
			if (u == null) {
				return super.getResourceURL(location);
			}
			return u;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Not found, delegating to parent: ");
			return super.getResourceURL(location);
		}
	}

	public InputStream getResourceStream(String location) throws IOException {
		// System.err.println("Stream: FILE: LOOKING FOR: "+location);
		System.err.println("Trying to locate in zip: "+location);
		ZipEntry z = zipFile.getEntry(location);
		InputStream is;
	    is = zipFile.getInputStream(z);
	    if(is!=null) {
	    	return is;
	    	// "+location+" base: "+baseFile);
	    }
		return super.getResourceStream(location);
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
