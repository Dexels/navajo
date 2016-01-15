package com.dexels.navajo.client.async.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.InputStreamContentProvider;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class AsyncTest {

	private static HttpClient client;

	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "https://reporting-test.sportlink.com/navajobirt/Postman";
		FileInputStream fileInputStream = new FileInputStream("/Users/frank/tml.xml");
		apacheclient(url, fileInputStream);
//		jetty9client(url, fileInputStream);
		


	}

	private static void apacheclient(String url, InputStream is) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		httppost.setEntity(new InputStreamEntity(is));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

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
	}
	private static void jetty9client(String url, FileInputStream fileInputStream) {
		client = new HttpClient();
	    
		 // max 200 concurrent connections to every address
		client.setMaxConnectionsPerDestination(10);

		client.setConnectTimeout(4000);
		// client.setThreadPool(executor);
		try {
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		client.POST(url).timeout(2000, TimeUnit.MILLISECONDS).header("Content-Type", "text/xml; charset=utf-8")
                .content(new InputStreamContentProvider(fileInputStream)).send(new BufferingResponseListener() {

				@Override
				public void onComplete(Result res) {
					if(res.isFailed()) {
						System.err.println("HTTP call failed: "+ res.getFailure());
						
						return;
					}
//					res.getResponse()
					byte[] content = getContent();
					System.err.println(new String(content));
					ByteArrayInputStream bais = new ByteArrayInputStream(content);
					try {
						Navajo response = NavajoFactory.getInstance().createNavajo(bais);
						response.write(System.err);
					} catch (RuntimeException e) {
						e.printStackTrace();
					} finally {
//						setActualCalls(getActualCalls()-1);
					}
				}});
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
