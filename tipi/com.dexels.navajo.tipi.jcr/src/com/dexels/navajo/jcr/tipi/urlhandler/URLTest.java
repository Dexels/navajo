package com.dexels.navajo.jcr.tipi.urlhandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLTest {
	
	
	private final static Logger logger = LoggerFactory.getLogger(URLTest.class);
	
	public void testUrl(String url) throws IOException {
		URL uu = new URL(url);
		URLConnection ss = uu.openConnection();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, ss.getInputStream());
		logger.info("Content: {}",new String(baos.toByteArray()));
	}
	
	  private final void copyResource(OutputStream out, InputStream in){
		  BufferedInputStream bin = new BufferedInputStream(in);
		  BufferedOutputStream bout = new BufferedOutputStream(out);
		  byte[] buffer = new byte[1024];
		  int read = -1;
		  boolean ready = false;
		  while (!ready) {
			  try {
				  read = bin.read(buffer);
				  if ( read > -1 ) {
					  bout.write(buffer,0,read);
				  }
			  } catch (IOException e) {
			  }
			  if ( read <= -1) {
				  ready = true;
			  }
		  }
		  try {
			  bin.close();
			  bout.flush();
			  bout.close();
		  } catch (IOException e) {

		  }
	  }
}
