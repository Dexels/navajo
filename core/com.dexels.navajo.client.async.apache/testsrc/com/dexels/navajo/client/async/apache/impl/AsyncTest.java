package com.dexels.navajo.client.async.apache.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncTest {

	
	private static final Logger logger = LoggerFactory.getLogger(AsyncTest.class);

	@Test
	public void testAsync() {
		// TODO
	}

	
	@SuppressWarnings("unused")
	private static void apacheclient(String url, CloseableHttpAsyncClient httpclient) throws IOException {
		FileInputStream is = new FileInputStream("/Users/frank/tml.xml");

		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new InputStreamEntity(is));

		//Execute and get the response.
		Future<HttpResponse> response = httpclient.execute(httppost, new FutureCallback<HttpResponse>() {
			
			@Override
			public void failed(Exception arg0) {
			}
			
			@Override
			public void completed(HttpResponse response) {
				HttpEntity entity = response.getEntity();
				try{
				if (entity != null) {
				    InputStream instream = entity.getContent();
				    ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    copyResource(baos, instream);
				    logger.info("Result: {}",new String(baos.toByteArray()));
				    try {
				        // do something useful
				    } finally {
				        instream.close();
				    }
				}				
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}
			
			@Override
			public void cancelled() {
				
			}
		});
		try {
			response.get();
		} catch (InterruptedException|ExecutionException e) {
			logger.error("Error: ", e);
		}

	}

	private static final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			read = bin.read(buffer);
			if (read > -1) {
				bout.write(buffer, 0, read);
			}
			if (read <= -1) {
				ready = true;
			}
		}
		try {
			bin.close();
			bout.flush();
			bout.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
}
