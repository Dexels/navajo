/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import com.dexels.navajo.document.types.Binary;



public class ElasticSearch {
	public static void main(String[] args) throws IOException {
		String url = "http://127.0.0.1:8080/fscrawler/_upload";
		
		URL serverUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
		 
		ElasticSearch.class.getResourceAsStream("test_java_file.txt");
		String fileUrl = "/Users/vgemistos/eclipse-workspace/com.dexels.elasticsearch/src/com/dexels/elasticsearch/test_java_file.txt";
		File fileToUpload = new File(fileUrl);
	
//		// Write the actual file contents
		InputStream inputStreamToLogFile = ElasticSearch.class.getResourceAsStream("test_java_file.txt");	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, inputStreamToLogFile);
		
		//Binary b;
		//b.guessContentType();
		//		 
//		outputStreamToRequestBody.flush();
//		
//		// Close the streams
//		outputStreamToRequestBody.close();
//		inputStreamToLogFile.close();
		
	    HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", new ByteArrayBody(baos.toByteArray(), ContentType.TEXT_PLAIN,"myname"))
                .addPart("id", new StringBody("simpleId",ContentType.TEXT_PLAIN))
                .build();

	    HttpPost request = new HttpPost(url);
	    	request.setEntity(entity);

	    HttpClient client = HttpClientBuilder.create().build();
	    HttpResponse response = client.execute(request);
		
		
		
		//System.out.println(urlConnection.getResponseCode());
		
	}
	
	public static void uploadBinary(Binary data, String id) {
		
	}
	private static void copyResource(OutputStream out, InputStream in) throws IOException {
		try(BufferedInputStream bin = new BufferedInputStream(in); BufferedOutputStream bout = new BufferedOutputStream(out);) {
			byte[] buffer = new byte[1024];
			int read = -1;
			boolean ready = false;
			while (!ready) {

				read = bin.read(buffer);
				System.err.println("data: "+read);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}

				if (read <= -1) {
					ready = true;
				}
			}
		}
	}
}

