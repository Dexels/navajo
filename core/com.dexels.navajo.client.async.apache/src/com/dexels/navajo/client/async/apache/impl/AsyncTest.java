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
		String url = "https://reporting.sportlink.com/navajobirt/Postman";
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		long before = System.currentTimeMillis();
		httpclient.start();
		for (int i = 0; i < 500; i++) {
			apacheclient(url,httpclient);
//			httpclient.close();
//			httpclient = HttpAsyncClients.createDefault();
//			apacheclient(url, fileInputStream,httpclient);
		}
		System.err.println("Time: "+ (System.currentTimeMillis()-before));
		//		jetty9client(url, fileInputStream);
		


	}

	private static void apacheclient(String url, CloseableHttpAsyncClient httpclient) throws ClientProtocolException, IOException {
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		httpclient.start();
		FileInputStream is = new FileInputStream("/Users/frank/tml.xml");

		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new InputStreamEntity(is));

		//Execute and get the response.
		Future<HttpResponse> response = httpclient.execute(httppost, new FutureCallback<HttpResponse>() {
			
			@Override
			public void failed(Exception arg0) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}
		});
		try {
			response.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//	private static void jetty9client(String url, FileInputStream fileInputStream) {
//		client = new HttpClient();
//	    
//		 // max 200 concurrent connections to every address
//		client.setMaxConnectionsPerDestination(10);
//
//		client.setConnectTimeout(4000);
//		// client.setThreadPool(executor);
//		try {
//			client.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//		client.POST(url).timeout(2000, TimeUnit.MILLISECONDS).header("Content-Type", "text/xml; charset=utf-8")
//                .content(new InputStreamContentProvider(fileInputStream)).send(new BufferingResponseListener() {
//
//				@Override
//				public void onComplete(Result res) {
//					if(res.isFailed()) {
//						System.err.println("HTTP call failed: "+ res.getFailure());
//						
//						return;
//					}
////					res.getResponse()
//					byte[] content = getContent();
//					System.err.println(new String(content));
//					ByteArrayInputStream bais = new ByteArrayInputStream(content);
//					try {
//						Navajo response = NavajoFactory.getInstance().createNavajo(bais);
//						response.write(System.err);
//					} catch (RuntimeException e) {
//						e.printStackTrace();
//					} finally {
////						setActualCalls(getActualCalls()-1);
//					}
//				}});
//	}
//
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
