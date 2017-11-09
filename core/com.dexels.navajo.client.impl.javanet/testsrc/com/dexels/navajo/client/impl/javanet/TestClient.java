package com.dexels.navajo.client.impl.javanet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestClient {

	public TestClient() {
	}

	@Test @Ignore
	public void testClient() throws ClientException {
		NavajoClient cl = new JavaNetNavajoClientImpl();
		cl.setAllowCompression(true);
		cl.setServerUrls(new String[] {"http://localhost:9090/stream/KNVB"});
		cl.setUsername("");
		cl.setPassword("");
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		Navajo result = cl.doSimpleSend(nc, "single");
		result.write(System.err);;
	}
	
	@Test @Ignore
	public void testDirect() throws IOException {
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("X-Navajo-Username", "");
		headers.put("X-Navajo-Password", "");
		headers.put("X-Navajo-Service", "single");

//		con.setRequestProperty("X-Navajo-RpcName", header.getRPCName());
//		con.setRequestProperty("X-Navajo-RpcUser", header.getRPCUser());
//		con.setRequestProperty("X-Navajo-Username", header.getRPCUser());
//		con.setRequestProperty("X-Navajo-Password", header.getRPCPassword());
//		con.setRequestProperty("X-Navajo-Service", header.getRPCName());
		String url = "http://localhost:9090/stream/KNVB";
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		StringWriter sw = new StringWriter();
		nc.write(sw);
		byte[] res = sendPOST(url, sw.toString().getBytes(), headers);
		System.err.println("RESULT: "+new String(res));
	}
	private static byte[] sendPOST(String url, byte[] data,Map<String,String> headers) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);

		for (Entry<String, String> e : headers.entrySet()) {
			con.setRequestProperty(e.getKey(), e.getValue());
		}
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(data);
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			copyResource(baos,con.getInputStream());
			return baos.toByteArray();
//			return con.getInputStream();
		} else {
			System.out.println("POST request not worked");
			return null;
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
