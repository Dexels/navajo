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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class AsyncTest {


	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "https://something.sportlink.com/navajobirt/Postman";
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		long before = System.currentTimeMillis();
		httpclient.start();
		for (int i = 0; i < 500; i++) {
			apacheclient(url,httpclient);
		}
		System.err.println("Time: "+ (System.currentTimeMillis()-before));
	}

	private static void apacheclient(String url, CloseableHttpAsyncClient httpclient) throws ClientProtocolException, IOException {
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
				    System.err.println("Result:\n"+new String(baos.toByteArray()));
				    try {
				        // do something useful
				    } finally {
				        instream.close();
				    }
				}				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void cancelled() {
				
			}
		});
		try {
			response.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
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

		}
	}
}
