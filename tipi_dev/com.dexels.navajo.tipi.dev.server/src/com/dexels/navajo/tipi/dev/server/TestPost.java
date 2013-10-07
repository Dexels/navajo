package com.dexels.navajo.tipi.dev.server;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class TestPost {
  public static void main(String[] args) throws IOException {
//    HttpClient client = new HttpClient();
//    GetMethod method = new GetMethod("http://requestb.in/1htbwf11");
    URL u = new URL("http://localhost:8181/github");
    URLConnection uc = u.openConnection();
    uc.setDoInput(true);
    uc.setDoOutput(true);
    FileInputStream fis = new FileInputStream("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/acceptance_settings_edit.json");
    final OutputStream outputStream = uc.getOutputStream();
	copyResource(outputStream,fis);
    fis.close();
    outputStream.close();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    copyResource(baos,  uc.getInputStream());
    System.err.println(">> "+new String(baos.toByteArray()));
    //    try {
//    	
//    	int statusCode = client.executeMethod(method);
//      byte[] responseBody = method.getResponseBody();
//      System.out.println(new String(responseBody));
//    } catch (Exception e) {
//      System.err.println("Fatal error: " + e.getMessage());
//      e.printStackTrace();
//    } finally {
//      method.releaseConnection();
//    }
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